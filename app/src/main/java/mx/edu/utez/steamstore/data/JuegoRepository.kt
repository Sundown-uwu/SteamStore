package mx.edu.utez.steamstore.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.edu.utez.steamstore.data.local.JuegoDao
import mx.edu.utez.steamstore.data.local.toDomain
import mx.edu.utez.steamstore.data.local.toEntity
import mx.edu.utez.steamstore.data.remote.JuegoApiService
import mx.edu.utez.steamstore.data.remote.MultipartHelper
import mx.edu.utez.steamstore.model.Juego
import java.io.File

/**
 * Interfaz del repositorio de juegos.
 * 
 * Esta es la única interfaz pública que debe usarse desde las capas superiores.
 * Encapsula tanto el acceso a la base de datos local (Room) como a la API remota.
 */
interface JuegoRepository {
    fun observarJuegos(): Flow<List<Juego>>
    suspend fun guardarJuego(juego: Juego, imagenFile: File? = null)
    fun observarJuegoPorId(id: Long): Flow<Juego?>
    suspend fun actualizarJuego(juego: Juego, imagenFile: File? = null)
    suspend fun eliminarJuego(juego: Juego)
    suspend fun sincronizarDesdeApi()
}

/**
 * Implementación del repositorio de juegos.
 * 
 * Combina el acceso a la base de datos local (Room) con la API remota (Retrofit).
 * La API es la fuente de verdad principal, y Room actúa como caché local.
 */
class DefaultJuegoRepository(
    private val juegoDao: JuegoDao,
    private val apiService: JuegoApiService
) : JuegoRepository {

    override fun observarJuegos(): Flow<List<Juego>> =
        juegoDao.observarJuegos().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun guardarJuego(juego: Juego, imagenFile: File?) {
        try {
            // Preparar los campos del formulario multipart
            val tituloPart = MultipartHelper.createPartFromString(juego.titulo)
            val descripcionPart = MultipartHelper.createPartFromString(juego.descripcion)
            val precioPart = MultipartHelper.createPartFromString(juego.precio.toString())
            val imagenPart = imagenFile?.let { MultipartHelper.createImagePart(it) }
            val urlPortadaPart = if (juego.urlPortada.isNotBlank()) {
                MultipartHelper.createPartFromString(juego.urlPortada)
            } else {
                null
            }

            // Llamar a la API
            val response = apiService.crearJuego(
                titulo = tituloPart,
                descripcion = descripcionPart,
                precio = precioPart,
                portada = imagenPart,
                urlPortada = urlPortadaPart
            )

            if (response.isSuccessful && response.body() != null) {
                val juegoCreado = response.body()!!
                // Guardar en Room como cache
                juegoDao.insertar(juegoCreado.toEntity())
            } else {
                throw Exception("Error al crear juego: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            // Si falla la API, guardar solo en local como fallback
            juegoDao.insertar(juego.toEntity())
            throw e
        }
    }

    override fun observarJuegoPorId(id: Long): Flow<Juego?> =
        juegoDao.observarJuegoPorId(id).map { it?.toDomain() }

    override suspend fun actualizarJuego(juego: Juego, imagenFile: File?) {
        try {
            // Preparar los campos del formulario multipart
            val tituloPart = MultipartHelper.createPartFromString(juego.titulo)
            val descripcionPart = MultipartHelper.createPartFromString(juego.descripcion)
            val precioPart = MultipartHelper.createPartFromString(juego.precio.toString())
            val imagenPart = imagenFile?.let { MultipartHelper.createImagePart(it) }
            val urlPortadaPart = if (juego.urlPortada.isNotBlank()) {
                MultipartHelper.createPartFromString(juego.urlPortada)
            } else {
                null
            }

            // Llamar a la API
            val response = apiService.actualizarJuego(
                id = juego.id,
                titulo = tituloPart,
                descripcion = descripcionPart,
                precio = precioPart,
                portada = imagenPart,
                urlPortada = urlPortadaPart
            )

            if (response.isSuccessful && response.body() != null) {
                val juegoActualizado = response.body()!!
                // Actualizar en Room como cache
                juegoDao.actualizar(juegoActualizado.toEntity())
            } else {
                throw Exception("Error al actualizar juego: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            // Si falla la API, actualizar solo en local como fallback
            juegoDao.actualizar(juego.toEntity())
            throw e
        }
    }

    override suspend fun eliminarJuego(juego: Juego) {
        try {
            // Llamar a la API
            val response = apiService.eliminarJuego(juego.id)

            if (response.isSuccessful) {
                // Eliminar de Room
                juegoDao.eliminar(juego.toEntity())
            } else {
                throw Exception("Error al eliminar juego: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            // Si falla la API, eliminar solo en local como fallback
            juegoDao.eliminar(juego.toEntity())
            throw e
        }
    }

    override suspend fun sincronizarDesdeApi() {
        try {
            val response = apiService.obtenerTodosLosJuegos()
            if (response.isSuccessful && response.body() != null) {
                val juegos = response.body()!!
                // Limpiar cache local y actualizar con datos de la API
                for (juego in juegos) {
                    juegoDao.insertar(juego.toEntity())
                }
            }
        } catch (e: Exception) {
            // Silenciar errores de sincronización
            e.printStackTrace()
        }
    }
}

