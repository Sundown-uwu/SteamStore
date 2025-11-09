package mx.edu.utez.steamstore.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.utez.steamstore.ui.screens.AddJuegoScreen
import mx.edu.utez.steamstore.ui.screens.DetalleJuegoScreen
import mx.edu.utez.steamstore.ui.screens.HomeScreen
import mx.edu.utez.steamstore.ui.screens.LoginScreen
import mx.edu.utez.steamstore.ui.screens.RecuperarScreen
import mx.edu.utez.steamstore.ui.screens.RegistroScreen
import mx.edu.utez.steamstore.viewModel.HomeViewModel

/**
 * Define el gráfico de navegación para toda la aplicación.
 *
 * Gestiona las transiciones entre las diferentes pantallas.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModel.Factory)
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
            HomeScreen(
                homeViewModel = homeViewModel,
                onNavigateToAddJuego = { navController.navigate("add_juego") },
                onNavigateToDetalle = { juegoId -> navController.navigate("detalle_juego/$juegoId") }
            )
        }
        composable("add_juego") {
            AddJuegoScreen(
                homeViewModel = homeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detalle_juego/{juegoId}",
            arguments = listOf(
                navArgument("juegoId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val juegoId = backStackEntry.arguments?.getLong("juegoId") ?: return@composable
            DetalleJuegoScreen(
                juegoId = juegoId,
                homeViewModel = homeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
