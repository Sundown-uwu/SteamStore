package mx.edu.utez.steamstore.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.steamstore.ui.screens.AddJuegoScreen
import mx.edu.utez.steamstore.ui.screens.HomeScreen
import mx.edu.utez.steamstore.ui.screens.LoginScreen
import mx.edu.utez.steamstore.ui.screens.RecuperarScreen
import mx.edu.utez.steamstore.ui.screens.RegistroScreen
import mx.edu.utez.steamstore.viewmodel.HomeViewModel

/**
 * Define el gráfico de navegación para toda la aplicación.
 *
 * Gestiona las transiciones entre las diferentes pantallas.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegistro = { navController.navigate("registro") },
                onNavigateToRecuperar = { navController.navigate("recuperar") }
            )
        }
        composable("registro") {
            RegistroScreen(onBack = { navController.popBackStack() })
        }
        composable("recuperar") {
            RecuperarScreen(onBack = { navController.popBackStack() })
        }
        composable("home") {
            // Se obtiene una instancia del HomeViewModel asociada al ciclo de vida del NavGraph.
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
                onNavigateToAddJuego = { navController.navigate("add_juego") }
            )
        }
        composable("add_juego") {
            AddJuegoScreen(onBack = { navController.popBackStack() })
        }
    }
}
