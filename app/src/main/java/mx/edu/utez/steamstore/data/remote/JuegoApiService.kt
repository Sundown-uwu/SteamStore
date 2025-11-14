package mx.edu.utez.steamstore.data.remote

import mx.edu.utez.steamstore.model.Juego
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * Interfaz de servicio Retrofit para la API de juegos.
 */
interface JuegoApiService {
    
    /**
     * Obtiene todos los juegos.
     */
    @GET("juegos")
    suspend fun obtenerTodosLosJuegos(): Response<List<Juego>>
    
    /**
     * Obtiene un juego por su ID.
     */
    @GET("juegos/{id}")
    suspend fun obtenerJuegoPorId(@Path("id") id: Long): Response<Juego>
    
    /**
     * Crea un nuevo juego con multipart/form-data.
     * 
     * @param titulo Título del juego
     * @param descripcion Descripción del juego
     * @param precio Precio del juego
     * @param portada Archivo de imagen (opcional)
     * @param urlPortada URL de la portada como string (opcional)
     */
    @Multipart
    @POST("juegos")
    suspend fun crearJuego(
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part portada: MultipartBody.Part?,
        @Part("urlPortada") urlPortada: RequestBody?
    ): Response<Juego>
    
    /**
     * Actualiza un juego existente con multipart/form-data.
     * 
     * @param id ID del juego a actualizar
     * @param titulo Título del juego
     * @param descripcion Descripción del juego
     * @param precio Precio del juego
     * @param portada Archivo de imagen (opcional)
     * @param urlPortada URL de la portada como string (opcional)
     */
    @Multipart
    @PUT("juegos/{id}")
    suspend fun actualizarJuego(
        @Path("id") id: Long,
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part portada: MultipartBody.Part?,
        @Part("urlPortada") urlPortada: RequestBody?
    ): Response<Juego>
    
    /**
     * Elimina un juego por su ID.
     */
    @DELETE("juegos/{id}")
    suspend fun eliminarJuego(@Path("id") id: Long): Response<Unit>
}

