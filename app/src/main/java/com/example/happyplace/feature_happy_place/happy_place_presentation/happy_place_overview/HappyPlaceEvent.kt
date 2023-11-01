package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview

sealed class HappyPlaceEvent {

    data class DeleteHappyPlace(val id: Int) : HappyPlaceEvent()

    // go from main act with this
    object AddHappyPlace : HappyPlaceEvent()

    object RestoreHappyPlace : HappyPlaceEvent()

    data class UpdateHappyPlace(val id: Int) : HappyPlaceEvent()

    data class ViewHappyPlace(val id: Int) : HappyPlaceEvent()
}

