package mx.edu.utez.steamstore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Un componente reutilizable para un botón de acción principal.
 *
 * Este Composable renderiza un `Button` que ocupa todo el ancho disponible
 * y tiene un padding superior para separarlo de otros elementos.
 *
 * @param texto El texto que se mostrará dentro del botón.
 * @param onClick La acción (lambda) que se ejecutará cuando el usuario presione el botón.
 */
@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(texto)
    }
}
