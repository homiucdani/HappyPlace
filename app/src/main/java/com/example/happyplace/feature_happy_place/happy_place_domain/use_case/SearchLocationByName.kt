package com.example.happyplace.feature_happy_place.happy_place_domain.use_case

import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.util.PlacesResult
import kotlinx.coroutines.flow.Flow

class SearchLocationByName(
    private val locationClient: LocationClient
) {

    operator fun invoke(query: String): Flow<PlacesResult> {
        return locationClient.searchLocationByName(query)
    }
}
