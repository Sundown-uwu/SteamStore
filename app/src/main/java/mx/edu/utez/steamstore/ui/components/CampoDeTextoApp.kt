package mx.edu.utez.steamstore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Un componente reutilizable para un campo de texto estándar.
 *
 * Este Composable envuelve un `OutlinedTextField` para mantener un estilo consistente
 * a través de la aplicación.
 *
 * @param valor El valor actual del campo de texto.
 * @param onValorCambiado Callback que se invoca cuando el valor del campo de texto cambia.
 * @param label El texto que se muestra como etiqueta (label) del campo de texto.
 */
@Composable
fun CampoDeTextoApp(
    valor: String,
    onValorCambiado: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambiado,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}
