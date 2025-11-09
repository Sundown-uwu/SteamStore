package mx.edu.utez.steamstore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [JuegoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SteamStoreDatabase : RoomDatabase() {

    abstract fun juegoDao(): JuegoDao

    companion object {
        @Volatile
        private var Instance: SteamStoreDatabase? = null

        fun getInstance(context: Context): SteamStoreDatabase =
            Instance ?: synchronized(this) {
                Instance ?: buildDatabase(context).also { Instance = it }
            }

        private fun buildDatabase(context: Context): SteamStoreDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                SteamStoreDatabase::class.java,
                "steam_store.db"
            ).build()
    }
}

