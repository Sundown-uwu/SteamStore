package mx.edu.utez.steamstore.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.edu.utez.steamstore.data.local.JuegoDao
import mx.edu.utez.steamstore.data.local.toDomain
import mx.edu.utez.steamstore.data.local.toEntity
import mx.edu.utez.steamstore.model.Juego

interface JuegoRepository {
    fun observarJuegos(): Flow<List<Juego>>
    suspend fun guardarJuego(juego: Juego)
    fun observarJuegoPorId(id: Long): Flow<Juego?>
    suspend fun actualizarJuego(juego: Juego)
    suspend fun eliminarJuego(juego: Juego)
}

class DefaultJuegoRepository(
    private val juegoDao: JuegoDao
) : JuegoRepository {

    override fun observarJuegos(): Flow<List<Juego>> =
        juegoDao.observarJuegos().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun guardarJuego(juego: Juego) {
        juegoDao.insertar(juego.toEntity())
    }

    override fun observarJuegoPorId(id: Long): Flow<Juego?> =
        juegoDao.observarJuegoPorId(id).map { it?.toDomain() }

    override suspend fun actualizarJuego(juego: Juego) {
        juegoDao.actualizar(juego.toEntity())
    }

    override suspend fun eliminarJuego(juego: Juego) {
        juegoDao.eliminar(juego.toEntity())
    }
}

