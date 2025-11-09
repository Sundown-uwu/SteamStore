package mx.edu.utez.steamstore.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.edu.utez.steamstore.model.Juego

@Entity(tableName = "juegos")
data class JuegoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String,
    @ColumnInfo(name = "url_portada")
    val urlPortada: String,
    @ColumnInfo(name = "precio")
    val precio: Double
)

fun JuegoEntity.toDomain() = Juego(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    urlPortada = urlPortada,
    precio = precio
)

fun Juego.toEntity() = JuegoEntity(
    id = if (id == 0L) 0 else id,
    titulo = titulo,
    descripcion = descripcion,
    urlPortada = urlPortada,
    precio = precio
)

