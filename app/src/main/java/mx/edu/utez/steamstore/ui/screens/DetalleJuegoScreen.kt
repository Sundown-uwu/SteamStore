package mx.edu.utez.steamstore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.edu.utez.steamstore.model.Juego
import mx.edu.utez.steamstore.ui.components.BotonPrincipal
import mx.edu.utez.steamstore.ui.components.CampoDeTextoApp
import mx.edu.utez.steamstore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleJuegoScreen(
    juegoId: Long,
    homeViewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val juegoState by homeViewModel.obtenerJuegoPorId(juegoId).collectAsState(initial = null)

    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var urlPortada by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(juegoState) {
        juegoState?.let { juego ->
            titulo = juego.titulo
            descripcion = juego.descripcion
            precio = String.format("%.2f", juego.precio)
            urlPortada = juego.urlPortada
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Juego") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { mostrarDialogoEliminar = true }) {
                        Text("Eliminar", color = Color.Red)
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
            if (juegoState == null) {
                Text(text = "Cargando juego...", modifier = Modifier.fillMaxSize())
                return@Column
            }

            CampoDeTextoApp(valor = titulo, onValorCambiado = { titulo = it }, label = "Título")
            CampoDeTextoApp(valor = descripcion, onValorCambiado = { descripcion = it }, label = "Descripción")
            CampoDeTextoApp(valor = precio, onValorCambiado = { precio = it }, label = "Precio")
            CampoDeTextoApp(valor = urlPortada, onValorCambiado = { urlPortada = it }, label = "URL Portada (drawable)")

            mensajeError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red
                )
            }

            BotonPrincipal(
                texto = "Guardar cambios",
                onClick = {
                    val precioNormalizado = precio.replace(',', '.')
                    val precioDouble = precioNormalizado.toDoubleOrNull()
                    val hayCamposVacios = titulo.isBlank() || descripcion.isBlank() || precioNormalizado.isBlank()

                    when {
                        hayCamposVacios -> mensajeError = "Por favor completa los campos obligatorios."
                        precioDouble == null -> mensajeError = "Introduce un precio válido (por ejemplo 49.99)."
                        else -> {
                            mensajeError = null
                            homeViewModel.actualizarJuego(
                                Juego(
                                    id = juegoId,
                                    titulo = titulo.trim(),
                                    descripcion = descripcion.trim(),
                                    urlPortada = urlPortada.ifBlank { "drawable/ic_launcher_foreground" },
                                    precio = precioDouble
                                )
                            )
                            onBack()
                        }
                    }
                }
            )
        }
    }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar juego") },
            text = { Text("¿Estás seguro de eliminar este juego? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        juegoState?.let {
                            homeViewModel.eliminarJuego(it)
                        }
                        mostrarDialogoEliminar = false
                        onBack()
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

