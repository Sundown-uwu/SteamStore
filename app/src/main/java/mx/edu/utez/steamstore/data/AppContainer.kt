package mx.edu.utez.steamstore.data

import android.content.Context
import mx.edu.utez.steamstore.data.local.SteamStoreDatabase
import mx.edu.utez.steamstore.data.remote.NetworkModule

/**
 * Contenedor de dependencias de la aplicación.
 * 
 * Centraliza la creación e inyección de todas las dependencias,
 * siguiendo el patrón de Dependency Injection.
 */
interface AppContainer {
    val juegoRepository: JuegoRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val database by lazy { SteamStoreDatabase.getInstance(context) }
    
    // Instancia única de Retrofit para toda la aplicación
    private val retrofit by lazy { NetworkModule.provideRetrofit() }
    
    // Instancia del servicio de API
    private val juegoApiService by lazy { 
        NetworkModule.provideJuegoApiService(retrofit) 
    }

    override val juegoRepository: JuegoRepository by lazy {
        DefaultJuegoRepository(
            juegoDao = database.juegoDao(),
            apiService = juegoApiService
        )
    }
}

