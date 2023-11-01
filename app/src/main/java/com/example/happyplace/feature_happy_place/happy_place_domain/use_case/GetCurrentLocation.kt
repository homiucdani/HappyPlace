package com.example.happyplace.feature_happy_place.happy_place_domain.use_case

import android.location.Location
import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient

class GetCurrentLocation(
    private val locationClient: LocationClient
) {
    suspend operator fun invoke(location: (Location) -> Unit) {
        return locationClient.getCurrentLocationOnce(location = location)
    }
}