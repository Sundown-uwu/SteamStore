package mx.edu.utez.steamstore.data.remote

/**
 * Configuración de la API.
 * 
 * IMPORTANTE: El servidor Flask DEBE estar ejecutándose antes de usar la app.
 * 
 * Para desarrollo local:
 * - Emulador Android: usar "http://10.0.2.2:5000" (10.0.2.2 es el alias del localhost del host)
 * - Dispositivo físico: usar la IP local que muestra Flask al iniciar
 *   Ejemplo: Si Flask muestra "Running on http://192.168.1.94:5000", usa "http://192.168.1.94:5000"
 * 
 * Cómo cambiar:
 * 1. Si usas EMULADOR: deja BASE_URL = "http://10.0.2.2:5000"
 * 2. Si usas DISPOSITIVO FÍSICO: cambia a tu IP local (la que muestra Flask)
 */
object ApiConfig {
    // ⚠️ CAMBIA ESTA URL SEGÚN TU ENTORNO ⚠️
    // Para EMULADOR (actual):
    const val BASE_URL = "http://10.0.2.2:5000"
    
    // Para DISPOSITIVO FÍSICO (descomenta y cambia la IP):
    // const val BASE_URL = "http://192.168.1.94:5000"  // Usa la IP que muestra Flask
    
    // Endpoint base de la API (debe terminar con / para Retrofit)
    const val API_BASE = "$BASE_URL/api/"
}

