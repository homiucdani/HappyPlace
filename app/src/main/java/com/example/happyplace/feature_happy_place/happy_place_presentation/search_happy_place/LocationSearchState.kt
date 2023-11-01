package com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place

import com.example.happyplace.feature_happy_place.happy_place_domain.util.PlacesResult

data class LocationSearchState(
    val placesResult: PlacesResult = PlacesResult.Idle,
    val isLoading:Boolean = false,
    val errorMessage:String? = null
)
