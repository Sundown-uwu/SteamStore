package mx.edu.utez.steamstore.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utez.steamstore.model.Juego

/**
 * ViewModel para la pantalla principal (Home).
 *
 * Se encarga de gestionar y exponer la lista de juegos a la UI.
 */
class HomeViewModel : ViewModel() {

    // Un StateFlow mutable y privado que contiene la lista de juegos.
    // Solo el ViewModel puede modificar esta lista.....
    private val _juegos = MutableStateFlow<List<Juego>>(emptyList())

    // Un StateFlow público e inmutable que expone la lista de juegos a la UI.
    // La UI observa este Flow para reaccionar a los cambios de datos.
    val juegos: StateFlow<List<Juego>> = _juegos

    /**
     * El bloque init se ejecuta cuando se crea una instancia del ViewModel.
     * Aquí se inicia la carga de los datos iniciales.
     */
    init {
        cargarJuegosDeEjemplo()
    }

    /**
     * Función privada que crea una lista de juegos de ejemplo y la asigna
     * al StateFlow `_juegos`.
     */
    private fun cargarJuegosDeEjemplo() {
        _juegos.value = listOf(
            Juego("1", "Cyberpunk 2077", "Juego de rol de acción y aventura de mundo abierto.", "drawable/cyberpunk", 59.99),
            Juego("2", "Elden Ring", "Juego de rol de acción de fantasía oscura.", "drawable/eldenring", 59.99),
            Juego("3", "The Witcher 3: Wild Hunt", "Juego de rol de acción de mundo abierto.", "drawable/witcher3", 39.99),
            Juego("4", "Red Dead Redemption 2", "Juego de acción y aventura de mundo abierto.", "drawable/rdr2", 59.99),
            Juego("5", "Stardew Valley", "Juego de simulación de agricultura.", "drawable/stardewvalley", 14.99)
        )
    }
}
