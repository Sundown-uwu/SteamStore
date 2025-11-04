package mx.edu.utez.steamstore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.steamstore.ui.components.BotonPrincipal
import mx.edu.utez.steamstore.ui.components.CampoDeTextoApp
import mx.edu.utez.steamstore.ui.components.CampoDeTextoContrasena

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CampoDeTextoApp(
                valor = email,
                onValorCambiado = { email = it },
                label = "Correo"
            )
            CampoDeTextoApp(
                valor = user,
                onValorCambiado = { user = it },
                label = "Usuario"
            )
            CampoDeTextoContrasena(
                valor = password,
                onValorCambiado = { password = it },
                label = "Contraseña"
            )
            BotonPrincipal(
                texto = "Registrarse",
                onClick = { /* Lógica de registro aquí */ }
            )
        }
    }
}
