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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.steamstore.ui.components.BotonPrincipal
import mx.edu.utez.steamstore.ui.components.CampoDeTextoApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJuegoScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Juego") },
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
            CampoDeTextoApp(valor = "", onValorCambiado = {}, label = "Título")
            CampoDeTextoApp(valor = "", onValorCambiado = {}, label = "Descripción")
            CampoDeTextoApp(valor = "", onValorCambiado = {}, label = "Precio")
            CampoDeTextoApp(valor = "", onValorCambiado = {}, label = "URL Portada (drawable)")
            BotonPrincipal(
                texto = "Guardar",
                onClick = { onBack() } // Simula guardar y regresar
            )
        }
    }
}
