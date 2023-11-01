package com.example.happyplace.feature_happy_place.happy_place_domain.happy_place

import java.time.LocalDate

data class HappyPlaceItem(
    val id: Int?,
    val title: String,
    val description: String,
    val date: LocalDate,
    val location: String,
    val photoBytes:ByteArray?,
    val latitudeLongitude: LatitudeLongitude
)
