package mx.edu.utez.steamstore.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Módulo de red que proporciona una instancia de Retrofit configurada.
 * 
 * Sigue el patrón de inyección de dependencias donde la instancia
 * se crea una vez y se reutiliza en toda la aplicación.
 */
object NetworkModule {
    
    /**
     * Crea y retorna una instancia de Retrofit configurada.
     */
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        return Retrofit.Builder()
            .baseUrl(ApiConfig.API_BASE)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    
    /**
     * Crea y retorna una instancia del servicio de API de juegos.
     */
    fun provideJuegoApiService(retrofit: Retrofit): JuegoApiService {
        return retrofit.create(JuegoApiService::class.java)
    }
}

