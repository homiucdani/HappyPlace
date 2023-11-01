package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.AddHappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.DeleteHappyPlace
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.GetHappyPlaceById
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.GetHappyPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HappyPlaceViewModel @Inject constructor(
    private val deleteHappyPlace: DeleteHappyPlace,
    private val addHappyPlaceItem: AddHappyPlaceItem,
    private val getHappyPlaceById: GetHappyPlaceById,
    private val getHappyPlaces: GetHappyPlaces,
) : ViewModel() {

    private val _state = MutableStateFlow(HappyPlaceState())

    val state = combine(_state, getHappyPlaces()) { state, happyPlaceItems ->

        if (state.happyPlaces != happyPlaceItems) {
            state.copy(
                happyPlaces = happyPlaceItems.mapNotNull { item ->
                    HappyPlaceItemUi(
                        id = item.id ?: return@mapNotNull null,
                        title = item.title,
                        description = item.description,
                        date = item.date,
                        location = item.location,
                        photoBytes = item.photoBytes,
                        latitudeLongitude = item.latitudeLongitude
                    )
                }
            )
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), HappyPlaceState())

    private var deletedHappyPlaceInstance: HappyPlaceItem? = null

    fun onEvent(event: HappyPlaceEvent) {
        when (event) {

            is HappyPlaceEvent.DeleteHappyPlace -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedHappyPlaceInstance = getHappyPlaceById(event.id)
                    deleteHappyPlace(event.id)
                }
            }

            HappyPlaceEvent.RestoreHappyPlace -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addHappyPlaceItem(deletedHappyPlaceInstance ?: return@launch)
                    deletedHappyPlaceInstance = null
                }
            }

            else -> Unit
        }
    }
}