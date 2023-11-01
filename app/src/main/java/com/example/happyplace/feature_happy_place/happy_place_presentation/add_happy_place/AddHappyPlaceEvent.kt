package com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place

import android.net.Uri
import java.time.LocalDate

sealed class AddHappyPlaceEvent {

    data class OnTitleEntered(val text: String) : AddHappyPlaceEvent()
    data class OnDescriptionEntered(val text: String) : AddHappyPlaceEvent()
    data class OnLocationEntered(val text: String) : AddHappyPlaceEvent()

    data class OnDatePicked(val localDate: LocalDate) : AddHappyPlaceEvent()

    data class OnLocationSearched(val location: String) : AddHappyPlaceEvent()

    data class OnPermissionResult(
        val permission: String,
        val isGranted: Boolean
    ) : AddHappyPlaceEvent()

    object OnDismissPermissionDialog : AddHappyPlaceEvent()

    object SelectCurrentLocation : AddHappyPlaceEvent()

    data class OnImagePicked(val image: Uri?) : AddHappyPlaceEvent()

    object LocationSearchClick : AddHappyPlaceEvent()

    data class OnPhotoTake(val imageUri:Uri?) : AddHappyPlaceEvent()

    object OpenPickAlertDialog : AddHappyPlaceEvent()
    object ClosePickAlertDialog : AddHappyPlaceEvent()

    data class SubmittedLocationResult(val locationResult: String?) : AddHappyPlaceEvent()

    object OnSaveHappyPlace : AddHappyPlaceEvent()
}
