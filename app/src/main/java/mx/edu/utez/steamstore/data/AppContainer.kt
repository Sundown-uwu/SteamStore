package mx.edu.utez.steamstore.data

import android.content.Context
import mx.edu.utez.steamstore.data.local.SteamStoreDatabase

interface AppContainer {
    val juegoRepository: JuegoRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val database by lazy { SteamStoreDatabase.getInstance(context) }

    override val juegoRepository: JuegoRepository by lazy {
        DefaultJuegoRepository(database.juegoDao())
    }
}

