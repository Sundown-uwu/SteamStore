package mx.edu.utez.steamstore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.steamstore.ui.components.GameCard
import mx.edu.utez.steamstore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onNavigateToAddJuego: () -> Unit,
    onNavigateToDetalle: (Long) -> Unit
) {
    val juegos by homeViewModel.juegos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Biblioteca") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddJuego) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Juego")
            }
        }
    ) { paddingValues ->
        if (juegos.isEmpty()) {
            Text(
                text = "Aún no hay juegos guardados. Usa el botón + para añadir uno.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(juegos) { juego ->
                    GameCard(
                        juego = juego,
                        onClick = { onNavigateToDetalle(juego.id) }
                    )
                }
            }
        }
    }
}
