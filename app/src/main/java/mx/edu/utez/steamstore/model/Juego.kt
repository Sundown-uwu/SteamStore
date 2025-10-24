
package mx.edu.utez.steamstore.model

/**
 * Representa el modelo de datos para un juego en la tienda.
 *
 * @property id El identificador único del juego.
 * @property titulo El título del juego.
 * @property descripcion Una breve descripción del juego.
 * @property urlPortada La referencia al recurso drawable para la portada del juego.
 * @property precio El precio del juego.
 */
data class Juego(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val urlPortada: String, // Se usará para un ID de drawable
    val precio: Double
)
