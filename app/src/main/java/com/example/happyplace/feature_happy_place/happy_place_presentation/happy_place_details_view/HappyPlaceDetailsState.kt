package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view

import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.LatitudeLongitude
import java.time.LocalDate

data class HappyPlaceDetailsState(
    val title: String = "",
    val description: String = "",
    val locationName: String = "",
    val latitudeLongitude: LatitudeLongitude = LatitudeLongitude(0.0, 0.0),
    val date:LocalDate = LocalDate.now(),
    val photoBytes: ByteArray? = null
)
