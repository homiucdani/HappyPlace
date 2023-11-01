package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview

data class HappyPlaceState(
    val happyPlaces: List<HappyPlaceItemUi> = emptyList(),
    val isLoading: Boolean = false,
)
