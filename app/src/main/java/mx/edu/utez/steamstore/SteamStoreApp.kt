package mx.edu.utez.steamstore

import android.app.Application
import mx.edu.utez.steamstore.data.AppContainer
import mx.edu.utez.steamstore.data.DefaultAppContainer

class SteamStoreApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}

