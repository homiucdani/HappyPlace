package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HappyPlaceMapScreen(
    state: HappyPlaceMapState,
    popBackStack: () -> Unit
) {

    val cameraPositionState = rememberCameraPositionState {
        CameraPosition.builder()
            .zoom(15f)
            .tilt(45f)
            .target(
                LatLng(
                    state.latitude,
                    state.longitude
                )
            )
    }

    LaunchedEffect(key1 = cameraPositionState) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder()
                    .zoom(15f)
                    .target(
                        LatLng(
                            state.latitude,
                            state.longitude
                        )
                    ).build()
            ),
            durationMs = 1000
        )
    }

    val markerState = rememberMarkerState(
        position = LatLng(state.latitude, state.longitude)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.locationName,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A89D5),
                    scrolledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(state = markerState, icon = BitmapDescriptorFactory.defaultMarker())
        }
    }


}