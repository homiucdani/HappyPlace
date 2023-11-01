package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view

sealed class HappyPlaceDetailsEvent {

    object PopBackStack : HappyPlaceDetailsEvent()

    object NavigateToMap : HappyPlaceDetailsEvent()
}
