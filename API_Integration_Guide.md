# Guía de Integración con API REST Flask

Esta guía documenta la integración de la aplicación Android con la API REST Flask usando Retrofit.

## 📋 Tabla de Contenidos

1. [Dependencias Agregadas](#dependencias-agregadas)
2. [Configuración de la API](#configuración-de-la-api)
3. [Estructura de Archivos](#estructura-de-archivos)
4. [Endpoints Disponibles](#endpoints-disponibles)
5. [Uso de la API](#uso-de-la-api)
6. [Configuración para Desarrollo](#configuración-para-desarrollo)
7. [Manejo de Imágenes](#manejo-de-imágenes)

---

## 🔧 Dependencias Agregadas

### En `gradle/libs.versions.toml`:

```toml
retrofit = "2.9.0"
gson = "2.10.1"
okhttp = "4.12.0"
```

### En `app/build.gradle.kts`:

```kotlin
// Retrofit y networking
implementation(libs.retrofit)
implementation(libs.retrofit.gson)
implementation(libs.gson)
implementation(libs.okhttp)
implementation(libs.okhttp.logging)
```

---

## ⚙️ Configuración de la API

### Permisos en `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### URL Base (`ApiConfig.kt`):

```kotlin
object ApiConfig {
    // Para emulador Android: usar "http://10.0.2.2:5000"
    // Para dispositivo físico: usar la IP local (ej: "http://192.168.1.100:5000")
    const val BASE_URL = "http://10.0.2.2:5000"
    const val API_BASE = "$BASE_URL/api"
}
```

**⚠️ IMPORTANTE:** Cambia la URL en `ApiConfig.kt` según tu entorno:
- **Emulador Android**: `http://10.0.2.2:5000` (10.0.2.2 es el alias del localhost del host)
- **Dispositivo físico**: Usa la IP local de tu máquina (ej: `http://192.168.1.100:5000`)

---

## 📁 Estructura de Archivos

```
app/src/main/java/mx/edu/utez/steamstore/
├── data/
│   ├── remote/
│   │   ├── ApiConfig.kt              # Configuración de URL base
│   │   ├── NetworkModule.kt          # Configuración de Retrofit
│   │   ├── JuegoApiService.kt        # Interfaz de servicio Retrofit
│   │   └── MultipartHelper.kt        # Helper para multipart/form-data
│   ├── JuegoRepository.kt             # Repositorio (actualizado para usar API)
│   └── AppContainer.kt               # Contenedor de dependencias (actualizado)
└── viewModel/
    └── HomeViewModel.kt                # ViewModel (actualizado)
```

---

## 🔌 Endpoints Disponibles

### 1. GET `/api/juegos`
Obtiene todos los juegos.

**Respuesta:** `List<Juego>`

### 2. GET `/api/juegos/{id}`
Obtiene un juego por ID.

**Parámetros:**
- `id` (Long): ID del juego

**Respuesta:** `Juego`

### 3. POST `/api/juegos`
Crea un nuevo juego.

**Content-Type:** `multipart/form-data`

**Campos:**
- `titulo` (String, requerido)
- `descripcion` (String, requerido)
- `precio` (String/Double, requerido)
- `portada` (File, opcional): Archivo de imagen
- `urlPortada` (String, opcional): URL de la portada

**Respuesta:** `Juego`

### 4. PUT `/api/juegos/{id}`
Actualiza un juego existente.

**Content-Type:** `multipart/form-data`

**Parámetros:**
- `id` (Long): ID del juego a actualizar

**Campos:** (mismos que POST)

**Respuesta:** `Juego`

### 5. DELETE `/api/juegos/{id}`
Elimina un juego.

**Parámetros:**
- `id` (Long): ID del juego a eliminar

**Respuesta:** `Unit`

---

## 💻 Uso de la API

### En el Repositorio

El `JuegoRepository` ahora usa la API como fuente principal y Room como caché local:

```kotlin
// Crear juego (con imagen opcional)
suspend fun guardarJuego(juego: Juego, imagenFile: File? = null)

// Actualizar juego (con imagen opcional)
suspend fun actualizarJuego(juego: Juego, imagenFile: File? = null)

// Eliminar juego
suspend fun eliminarJuego(juego: Juego)

// Sincronizar desde API
suspend fun sincronizarDesdeApi()
```

### En el ViewModel

```kotlin
// Guardar juego
homeViewModel.guardarJuego(juego, imagenFile)

// Actualizar juego
homeViewModel.actualizarJuego(juego, imagenFile)

// Eliminar juego
homeViewModel.eliminarJuego(juego)

// Sincronizar desde API
homeViewModel.sincronizarDesdeApi()
```

---

## 🛠️ Configuración para Desarrollo

### Para Emulador Android:

1. Asegúrate de que tu servidor Flask esté corriendo en `localhost:5000`
2. En `ApiConfig.kt`, usa: `http://10.0.2.2:5000`
3. El emulador usa `10.0.2.2` como alias para el `localhost` de tu máquina

### Para Dispositivo Físico:

1. Encuentra la IP local de tu máquina:
   - Windows: `ipconfig` en CMD
   - Linux/Mac: `ifconfig` o `ip addr`
2. Asegúrate de que tu dispositivo y tu máquina estén en la misma red WiFi
3. En `ApiConfig.kt`, usa: `http://[TU_IP_LOCAL]:5000`
4. Asegúrate de que el firewall permita conexiones en el puerto 5000

### Verificar Conexión:

Puedes verificar que la API está accesible desde tu dispositivo/emulador abriendo:
- `http://10.0.2.2:5000/api/juegos` (emulador)
- `http://[TU_IP]:5000/api/juegos` (dispositivo físico)

---

## 🖼️ Manejo de Imágenes

### Subir Imagen desde Archivo:

```kotlin
val imagenFile = File("/path/to/image.jpg")
homeViewModel.guardarJuego(juego, imagenFile)
```

### Subir Imagen desde Uri:

```kotlin
import mx.edu.utez.steamstore.data.remote.MultipartHelper
import android.net.Uri

val imageUri: Uri = // ... obtener Uri de la galería
val imagePart = MultipartHelper.createImagePartFromUri(context, imageUri)
// Luego usar en el repositorio
```

### Helper MultipartHelper:

El `MultipartHelper` proporciona funciones útiles:

```kotlin
// Crear RequestBody desde String
MultipartHelper.createPartFromString("valor")

// Crear MultipartBody.Part desde File
MultipartHelper.createImagePart(file, "portada")

// Crear MultipartBody.Part desde Uri
MultipartHelper.createImagePartFromUri(context, uri, "portada")
```

---

## 🔍 Logging y Debugging

El `NetworkModule` incluye un interceptor de logging que muestra todas las peticiones y respuestas HTTP en Logcat.

**Nivel de logging:** `HttpLoggingInterceptor.Level.BODY`

Para ver los logs, filtra por tag: `OkHttp`

---

## ⚠️ Manejo de Errores

El repositorio implementa un patrón de fallback:

1. **Intenta operación en la API**
2. **Si falla, guarda/actualiza en Room local como fallback**
3. **Lanza excepción para que la UI pueda manejarla**

Ejemplo de manejo en la UI:

```kotlin
try {
    homeViewModel.guardarJuego(juego)
} catch (e: Exception) {
    // Mostrar mensaje de error al usuario
    Log.e("Error", "Error al guardar juego", e)
}
```

---

## 📝 Notas Importantes

1. **Sincronización**: La app usa Room como caché local. Los datos se sincronizan automáticamente cuando se realizan operaciones CRUD.

2. **Offline**: Si la API no está disponible, las operaciones se guardan localmente en Room como fallback.

3. **Imágenes**: Las imágenes se suben como `multipart/form-data`. Si no se proporciona una imagen, se puede usar `urlPortada` como alternativa.

4. **Timeouts**: Los timeouts están configurados a 30 segundos para operaciones de red.

5. **GSON**: La serialización/deserialización se maneja automáticamente con GSON.

---

## 🚀 Próximos Pasos

1. Configurar la URL correcta en `ApiConfig.kt` según tu entorno
2. Asegurarte de que tu servidor Flask esté corriendo
3. Probar las operaciones CRUD desde la app
4. Verificar los logs en Logcat para debugging
5. Implementar manejo de errores en la UI si es necesario

---

## 📚 Referencias

- [Retrofit Documentation](https://square.github.io/retrofit/)
- [OkHttp Documentation](https://square.github.io/okhttp/)
- [GSON Documentation](https://github.com/google/gson)

