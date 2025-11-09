package mx.edu.utez.steamstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.steamstore.model.Juego

@Composable
fun GameCard(juego: Juego, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            val context = LocalContext.current
            val imageId = remember(juego.urlPortada) {
                val resourceName = juego.urlPortada.substringAfterLast('/')
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            }

            if (imageId != 0) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = juego.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback en caso de que el drawable no se encuentre
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.DarkGray)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = juego.titulo, style = MaterialTheme.typography.titleLarge)
                val precioFormateado = remember(juego.precio) {
                    String.format("$%.2f", juego.precio)
                }
                Text(text = precioFormateado, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}
