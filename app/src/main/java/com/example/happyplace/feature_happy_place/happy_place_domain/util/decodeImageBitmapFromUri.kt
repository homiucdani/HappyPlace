package com.example.happyplace.feature_happy_place.happy_place_domain.util

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.example.happyplace.R

@Composable
fun decodeImageBitmapFromUriWithPermission(imageUri: Uri): ImageBitmap? {
    val context = LocalContext.current
    try {
        context.contentResolver?.let { contentRes ->
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentRes.takePersistableUriPermission(imageUri, flag)

            return contentRes.openInputStream(imageUri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap.asImageBitmap()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ImageBitmap.imageResource(id = R.drawable.profile_image_placeholder)
}

@Composable
fun decodeImageBitmapFromUri(imageUri: Uri): ImageBitmap {
    val context = LocalContext.current
    try {
        context.contentResolver?.let { contentRes ->
            return contentRes.openInputStream(imageUri).use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap.asImageBitmap()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ImageBitmap.imageResource(id = R.drawable.profile_image_placeholder)
}