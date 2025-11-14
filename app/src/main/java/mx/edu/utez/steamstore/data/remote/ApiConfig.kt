package mx.edu.utez.steamstore.data.remote

/**
 * Configuración de la API.
 * 
 * Para desarrollo local:
 * - Emulador Android: usar "http://10.0.2.2:5000"
 * - Dispositivo físico: usar la IP local de tu máquina (ej: "http://192.168.1.100:5000")
 */
object ApiConfig {
    // Cambia esta URL según tu entorno
    const val BASE_URL = "http://127.0.0.1:5000"
    
    // Endpoint base de la API
    const val API_BASE = "$BASE_URL/api"
}

