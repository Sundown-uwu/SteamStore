# Guía de cambios para CRUD de Juegos

Esta guía resume los archivos y dependencias incorporados para habilitar el alta, la consulta, la edición y la eliminación de juegos en la app.

## Dependencias y configuración

- `gradle/libs.versions.toml`
  - Se agregó la versión `room = "2.6.1"`.
  - Nuevas entradas: `androidx-room-runtime`, `androidx-room-ktx`, `androidx-room-compiler`, `kotlin-kapt`.
- `build.gradle.kts` (nivel raíz)
  - Se añadió el plugin `alias(libs.plugins.kotlin.kapt) apply false`.
- `app/build.gradle.kts`
  - Se aplicó el plugin `alias(libs.plugins.kotlin.kapt)`.
  - Se actualizó la configuración de Java/Kotlin a la versión 17.
  - Se agregaron las dependencias de Room (`runtime`, `ktx`, `compiler` vía `kapt`).

## Capa de datos (Room)

- `app/src/main/java/mx/edu/utez/steamstore/data/local/JuegoEntity.kt`
  - Entidad Room que representa cada juego.
- `app/src/main/java/mx/edu/utez/steamstore/data/local/JuegoDao.kt`
  - DAO con métodos para observar, insertar, actualizar y eliminar juegos.
- `app/src/main/java/mx/edu/utez/steamstore/data/local/SteamStoreDatabase.kt`
  - Base de datos Room que expone el `JuegoDao`.
- `app/src/main/java/mx/edu/utez/steamstore/data/JuegoRepository.kt`
  - Repositorio que mapea entre entidad y modelo de dominio, y delega al DAO.
- `app/src/main/java/mx/edu/utez/steamstore/data/AppContainer.kt`
  - Contenedor simple de dependencias para exponer el repositorio.
- `app/src/main/java/mx/edu/utez/steamstore/SteamStoreApp.kt`
  - `Application` que inicializa el `AppContainer`. Referenciado desde `AndroidManifest.xml`.

## Modelo y ViewModel

- `app/src/main/java/mx/edu/utez/steamstore/model/Juego.kt`
  - Ajuste del `id` a `Long` para ser compatible con Room.
- `app/src/main/java/mx/edu/utez/steamstore/viewModel/HomeViewModel.kt`
  - Integración del repositorio, exposición del flujo de juegos y funciones `guardar`, `obtener`, `actualizar`, `eliminar`.

## Navegación y UI

- `app/src/main/java/mx/edu/utez/steamstore/navigation/AppNavigation.kt`
  - Nueva ruta `detalle_juego/{juegoId}` y reuso del mismo `HomeViewModel`.
- `app/src/main/java/mx/edu/utez/steamstore/ui/screens/HomeScreen.kt`
  - Navegación al detalle al seleccionar un juego.
- `app/src/main/java/mx/edu/utez/steamstore/ui/screens/AddJuegoScreen.kt`
  - Formulario de alta con validaciones básicas.
- `app/src/main/java/mx/edu/utez/steamstore/ui/screens/DetalleJuegoScreen.kt`
  - Pantalla para editar y eliminar juegos con confirmación.
- `app/src/main/java/mx/edu/utez/steamstore/ui/components/GameCard.kt`
  - Ajuste para formatear el precio y responder al click.

## Cómo probar el flujo CRUD

1. Ejecuta `.\gradlew.bat assembleDebug` o usa el botón *Run* en Android Studio.
2. Lanza un emulador o conecta un dispositivo con depuración USB.
3. Instala la app y navega a:
   - **Añadir Juego** para crear nuevas entradas.
   - Selecciona un juego en la lista para abrir **Detalle del Juego**, editar y guardar.
   - Usa el botón **Eliminar** dentro del detalle para borrar el registro.

Con estos archivos y configuraciones, la aplicación queda lista para operar con SQLite mediante Room en todo el ciclo CRUD.

