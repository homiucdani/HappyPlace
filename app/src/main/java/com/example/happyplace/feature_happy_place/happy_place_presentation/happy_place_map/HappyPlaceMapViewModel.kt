package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HappyPlaceMapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(HappyPlaceMapState())
    val state: State<HappyPlaceMapState> = _state

    init {
        val latitude = savedStateHandle.get<String>("latitude")
        val longitude = savedStateHandle.get<String>("longitude")
        val locationName = savedStateHandle.get<String>("locationName")

        if (latitude != null && longitude != null && locationName != null) {
            _state.value = state.value.copy(
                latitude = latitude.toDouble(),
                longitude = longitude.toDouble(),
                locationName = locationName
            )
        }
    }
}