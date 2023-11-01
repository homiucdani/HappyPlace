package com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.LatitudeLongitude
import java.time.LocalDate

data class AddHappyPlaceState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val location: String = "",
    val photoBytes: ByteArray? = null,
    val latitudeLongitude: LatitudeLongitude = LatitudeLongitude(0.0, 0.0),
    val isLocationGranted: Boolean = false,
    val isStorageGranted: Boolean = false,
    val visiblePermissionDialogQueue: SnapshotStateList<String> = mutableStateListOf(),
    val isPickAlertDialogOpen: Boolean = false
)
