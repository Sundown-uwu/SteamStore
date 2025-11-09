package mx.edu.utez.steamstore.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {

    @Query("SELECT * FROM juegos ORDER BY id DESC")
    fun observarJuegos(): Flow<List<JuegoEntity>>

    @Query("SELECT * FROM juegos WHERE id = :id LIMIT 1")
    fun observarJuegoPorId(id: Long): Flow<JuegoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(juego: JuegoEntity)

    @Update
    suspend fun actualizar(juego: JuegoEntity)

    @Delete
    suspend fun eliminar(juego: JuegoEntity)
}

