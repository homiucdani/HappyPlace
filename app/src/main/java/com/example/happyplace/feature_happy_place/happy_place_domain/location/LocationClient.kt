package com.example.happyplace.feature_happy_place.happy_place_domain.location

import android.location.Location
import com.example.happyplace.feature_happy_place.happy_place_domain.util.PlacesResult
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    suspend fun getCurrentLocationOnce(location: (Location) -> Unit)

    fun searchLocationByName(query: String): Flow<PlacesResult>

    class LocationException(message: String) : Exception(message)
}