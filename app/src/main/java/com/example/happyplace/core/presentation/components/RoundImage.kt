package com.example.happyplace.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.happyplace.R
import com.example.happyplace.core.presentation.util.rememberBitmapFromBytes


@Composable
fun RoundImage(
    modifier: Modifier = Modifier,
    photoBytes: ByteArray?,
) {

    val imgBitmap = rememberBitmapFromBytes(byteArray = photoBytes)

    if (imgBitmap != null) {
        Image(
            bitmap = imgBitmap,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.profile_image_placeholder),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}