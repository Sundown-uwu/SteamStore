package mx.edu.utez.steamstore.data.remote

import android.net.Uri
import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * Helper para crear RequestBody y MultipartBody.Part para las peticiones multipart.
 */
object MultipartHelper {
    
    /**
     * Crea un RequestBody a partir de un String.
     */
    fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
    
    /**
     * Crea un MultipartBody.Part a partir de un archivo de imagen.
     * 
     * @param file Archivo de imagen
     * @param fieldName Nombre del campo en el formulario (por defecto "portada")
     */
    fun createImagePart(file: File, fieldName: String = "portada"): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fieldName, file.name, requestFile)
    }
    
    /**
     * Crea un MultipartBody.Part a partir de un Uri de imagen.
     * 
     * @param context Contexto de la aplicación
     * @param imageUri Uri de la imagen
     * @param fieldName Nombre del campo en el formulario (por defecto "portada")
     */
    fun createImagePartFromUri(
        context: Context,
        imageUri: Uri,
        fieldName: String = "portada"
    ): MultipartBody.Part? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            
            createImagePart(file, fieldName)
        } catch (e: Exception) {
            null
        }
    }
}

