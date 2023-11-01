package com.example.happyplace.feature_happy_place.happy_place_domain.util

import com.google.android.libraries.places.api.model.AutocompletePrediction

sealed class PlacesResult(
    val searchList: MutableList<AutocompletePrediction> = mutableListOf(),
    val message: String? = null
) {

    data class Success(
        val searchListResult: MutableList<AutocompletePrediction>
    ) : PlacesResult(searchList = searchListResult)

    object Idle : PlacesResult()

    data class Error(val messageError: String) : PlacesResult(message = messageError)
}
