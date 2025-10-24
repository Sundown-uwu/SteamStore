package mx.edu.utez.steamstore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

/**
 * Un componente reutilizable para un campo de texto de contraseña.
 *
 * Este Composable ofusca la entrada del usuario (muestra puntos en lugar de caracteres)
 * y establece el tipo de teclado adecuado para contraseñas.
 * El nombre del archivo y la función usan "Contrasena" en lugar de "Contraseña" para
 * evitar posibles errores de compilación relacionados con caracteres especiales.
 *
 * @param valor El valor actual del campo de texto.
 * @param onValorCambiado Callback que se invoca cuando el valor del campo de texto cambia.
 * @param label El texto que se muestra como etiqueta (label) del campo de texto.
 */
@Composable
fun CampoDeTextoContrasena(
    valor: String,
    onValorCambiado: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambiado,
        label = { Text(label) },
        // Oculta el texto y lo reemplaza por puntos.
        visualTransformation = PasswordVisualTransformation(),
        // Configura el teclado para que sea de tipo contraseña.
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}
