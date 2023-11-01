package com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happyplace.core.presentation.util.UiEvent
import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.SearchLocationByName
import com.example.happyplace.feature_happy_place.happy_place_domain.util.PlacesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val searchLocationByName: SearchLocationByName
) : ViewModel() {

    private val _state = MutableStateFlow(LocationSearchState())
    val state: StateFlow<LocationSearchState> = _state

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _oneTimeEvent = MutableSharedFlow<UiEvent>()
    val oneTimeEvent = _oneTimeEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _searchQuery.debounce(1000).collectLatest { result ->
                searchLocation(result)
            }
        }
    }

    fun onEvent(event: LocationSearchEvent) {
        when (event) {
            is LocationSearchEvent.OnLocationEntered -> {
                _searchQuery.value = event.text
            }

            LocationSearchEvent.ClearLocationText -> {
                _searchQuery.value = ""
            }

            else -> Unit
        }
    }

    private fun searchLocation(query: String) {
        viewModelScope.launch {

            try {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                searchLocationByName(query).collectLatest { placeResult ->
                    when (placeResult) {
                        is PlacesResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = placeResult.messageError
                                )
                            }
                        }

                        is PlacesResult.Success -> {
                            _state.update {
                                it.copy(
                                    placesResult = placeResult,
                                    isLoading = false
                                )
                            }
                        }

                        else -> Unit
                    }
                }
            } catch (e: LocationClient.LocationException) {
                if (e.message != null) {
                    _oneTimeEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Unknown error."))
                    _state.update {
                        it.copy(
                            errorMessage = e.message ?: "Unknown error."
                        )
                    }
                }
            }
        }
    }
}