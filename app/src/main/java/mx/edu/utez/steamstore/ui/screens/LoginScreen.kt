package mx.edu.utez.steamstore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.steamstore.ui.components.BotonPrincipal
import mx.edu.utez.steamstore.ui.components.CampoDeTextoApp
import mx.edu.utez.steamstore.ui.components.CampoDeTextoContrasena

/**
 * Define la pantalla de inicio de sesión de la aplicación.
 *
 * @param onLoginSuccess Callback que se invoca cuando el inicio de sesión es exitoso.
 * @param onNavigateToRegistro Callback para navegar a la pantalla de registro.
 * @param onNavigateToRecuperar Callback para navegar a la pantalla de recuperación de contraseña.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegistro: () -> Unit,
    onNavigateToRecuperar: () -> Unit
) {
    // Estados para almacenar el valor actual de los campos de texto de usuario y contraseña.
    // `remember` y `mutableStateOf` se usan para que Compose recomponga la UI cuando estos valores cambien.
    val usuario = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // `Column` se utiliza para organizar los elementos de la UI verticalmente.
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible.
            .padding(16.dp), // Añade un padding alrededor del contenido.
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente.
    ) {
        // Título o logo de la aplicación.
        Text("MiSteam", fontSize = 24.sp)

        // Componente reutilizable para el campo de texto del usuario.
        CampoDeTextoApp(
            valor = usuario.value,
            onValorCambiado = { usuario.value = it }, // Actualiza el estado `usuario` cada vez que el texto cambia.
            label = "Usuario"
        )

        // Componente reutilizable para el campo de texto de la contraseña.
        // Se utiliza `CampoDeTextoContrasena` (sin 'ñ') para evitar errores de compilación.
        CampoDeTextoContrasena(
            valor = password.value,
            onValorCambiado = { password.value = it }, // Actualiza el estado `password`.
            label = "Contraseña"
        )

        // Botón principal para ejecutar la acción de inicio de sesión.
        BotonPrincipal(
            texto = "Iniciar Sesión",
            onClick = {
                // Lógica de autenticación "hardcodeada" (solo para demostración).
                if (usuario.value == "admin" && password.value == "123") {
                    onLoginSuccess() // Si es exitoso, llama al callback para navegar a la pantalla principal.
                }
            }
        )

        // Botón de texto para navegar a la pantalla de registro.
        TextButton(onClick = onNavigateToRegistro) {
            Text("Registrarse")
        }

        // Botón de texto para navegar a la pantalla de recuperación de contraseña.
        TextButton(onClick = onNavigateToRecuperar) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}
