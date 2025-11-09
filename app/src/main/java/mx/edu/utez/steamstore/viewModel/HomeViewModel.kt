package mx.edu.utez.steamstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.steamstore.SteamStoreApp
import mx.edu.utez.steamstore.data.JuegoRepository
import mx.edu.utez.steamstore.model.Juego

/**
 * ViewModel para la pantalla principal (Home).
 *
 * Se encarga de gestionar y exponer la lista de juegos a la UI.
 */
class HomeViewModel(
    private val juegoRepository: JuegoRepository
) : ViewModel() {

    val juegos: StateFlow<List<Juego>> =
        juegoRepository
            .observarJuegos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun guardarJuego(juego: Juego) {
        viewModelScope.launch {
            juegoRepository.guardarJuego(juego)
        }
    }

    fun obtenerJuegoPorId(id: Long): Flow<Juego?> = juegoRepository.observarJuegoPorId(id)

    fun actualizarJuego(juego: Juego) {
        viewModelScope.launch {
            juegoRepository.actualizarJuego(juego)
        }
    }

    fun eliminarJuego(juego: Juego) {
        viewModelScope.launch {
            juegoRepository.eliminarJuego(juego)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SteamStoreApp)
                val repository = application.container.juegoRepository
                HomeViewModel(repository)
            }
        }
    }
}
