package com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.happyplace.core.presentation.util.UiEvent
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.LatitudeLongitude
import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.AddHappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.GetCurrentLocation
import com.example.happyplace.feature_happy_place.happy_place_domain.use_case.GetHappyPlaceById
import com.example.happyplace.feature_happy_place.happy_place_domain.util.InvalidHappyPlaceException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddHappyPlaceViewModel @Inject constructor(
    private val getHappyPlaceById: GetHappyPlaceById,
    private val addHappyPlaceItem: AddHappyPlaceItem,
    private val savedStateHandle: SavedStateHandle,
    private val getCurrentLocation: GetCurrentLocation,
    private val geocoder: Geocoder,
    private val application: Application
) : AndroidViewModel(application) {

    private fun getContext(): Context = getApplication<Application>().applicationContext

    private val _state = MutableStateFlow(AddHappyPlaceState())

    val state: StateFlow<AddHappyPlaceState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private var currentId: Int? = null

    private var getCurrentLocationJob: Job? = null

    init {
        val happyPlaceId = savedStateHandle.getStateFlow("happyPlaceId", -1).value
        if (happyPlaceId != -1) {
            viewModelScope.launch {
                val happyPlace = getHappyPlaceById(happyPlaceId)
                _state.update {
                    it.copy(
                        id = currentId,
                        title = happyPlace.title,
                        description = happyPlace.description,
                        date = happyPlace.date,
                        location = happyPlace.location,
                        photoBytes = happyPlace.photoBytes,
                        latitudeLongitude = happyPlace.latitudeLongitude
                    )
                }
                currentId = happyPlaceId
            }
        }
    }

    fun onEvent(event: AddHappyPlaceEvent) {
        when (event) {
            is AddHappyPlaceEvent.OnDatePicked -> {
                _state.update {
                    it.copy(
                        date = event.localDate
                    )
                }
            }

            is AddHappyPlaceEvent.OnDescriptionEntered -> {
                _state.update {
                    it.copy(
                        description = event.text
                    )
                }
            }

            is AddHappyPlaceEvent.OnImagePicked -> {
                getImageGalleryBytes(event.image)
            }

            is AddHappyPlaceEvent.OnLocationSearched -> {
                _state.update {
                    it.copy(
                        location = event.location
                    )
                }
            }

            is AddHappyPlaceEvent.OnTitleEntered -> {
                _state.update {
                    it.copy(
                        title = event.text
                    )
                }
            }

            AddHappyPlaceEvent.OnSaveHappyPlace -> {
                addHappyPlace(state.value)
            }

            is AddHappyPlaceEvent.OnPermissionResult -> {
                if (!event.isGranted && !state.value.visiblePermissionDialogQueue.contains(event.permission)) {
                    _state.value.visiblePermissionDialogQueue.add(0, event.permission)
                }
            }
            //    2      1
            // [CAMERA, MIC]
            AddHappyPlaceEvent.OnDismissPermissionDialog -> {
                _state.value.visiblePermissionDialogQueue.removeLast()
            }

            is AddHappyPlaceEvent.OnLocationEntered -> {
                _state.update {
                    it.copy(
                        location = event.text
                    )
                }
            }

            is AddHappyPlaceEvent.OnPhotoTake -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getTakenPhotoGalleryBytes(event.imageUri)
                }
            }

            is AddHappyPlaceEvent.SubmittedLocationResult -> {
                _state.update {
                    it.copy(
                        location = event.locationResult ?: it.location
                    )
                }
            }

            AddHappyPlaceEvent.OpenPickAlertDialog -> {
                _state.update {
                    it.copy(
                        isPickAlertDialogOpen = true
                    )
                }
            }

            AddHappyPlaceEvent.ClosePickAlertDialog -> {
                _state.update {
                    it.copy(
                        isPickAlertDialogOpen = false
                    )
                }
            }

            AddHappyPlaceEvent.SelectCurrentLocation -> {
                getLocation()
            }

            else -> Unit
        }
    }

    private fun getTakenPhotoGalleryBytes(imageUri: Uri?) {
        imageUri?.let { imgUri ->
            getContext().contentResolver.openInputStream(imgUri)?.use { inputStream ->
                _state.update {
                    it.copy(
                        photoBytes = inputStream.readBytes()
                    )
                }
            }
        }
    }

    fun createImageUri(): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageName = "JPEG_$timestamp.jpg"
        val resolver = getContext().contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    private fun getImageGalleryBytes(imageUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            imageUri?.let { imgUri ->
                getContext().contentResolver.openInputStream(imgUri)?.use { inputStream ->
                    _state.update {
                        it.copy(
                            photoBytes = inputStream.readBytes()
                        )
                    }
                }
            }
        }
    }

    private fun getLocation() {
        getCurrentLocationJob?.cancel()
        getCurrentLocationJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                getCurrentLocation { location ->
                    _state.update {
                        it.copy(
                            latitudeLongitude = it.latitudeLongitude.copy(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    }
                    getLocationNameByLatLng(state.value.latitudeLongitude)
                }
            } catch (e: LocationClient.LocationException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Unknown error."))
            }
        }
    }

    private fun getLocationNameByLatLng(latitudeLongitude: LatitudeLongitude) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                latitudeLongitude.latitude,
                latitudeLongitude.longitude,
                1,
                Geocoder.GeocodeListener { addresses ->
                    addresses[0].getAddressLine(0)?.let { address ->
                        _state.update {
                            it.copy(
                                location = address
                            )
                        }
                    }
                }
            )
        } else {
            val addresses = geocoder.getFromLocation(
                latitudeLongitude.latitude,
                latitudeLongitude.longitude,
                1
            )
            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0)?.let { address ->
                    _state.update {
                        it.copy(
                            location = address
                        )
                    }
                }
            }
        }
    }

    private fun addHappyPlace(state: AddHappyPlaceState) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addHappyPlaceItem(
                    HappyPlaceItem(
                        id = currentId,
                        title = state.title,
                        description = state.description,
                        date = state.date,
                        location = state.location,
                        photoBytes = state.photoBytes,
                        latitudeLongitude = state.latitudeLongitude
                    )
                )
                _uiEvent.emit(UiEvent.SaveHappyPlace)
            } catch (e: InvalidHappyPlaceException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Couldn't save note."))
            }
        }
    }
}