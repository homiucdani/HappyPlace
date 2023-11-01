package com.example.happyplace.feature_happy_place.happy_place_domain.use_case

import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceRepository
import com.example.happyplace.feature_happy_place.happy_place_domain.util.InvalidHappyPlaceException

class AddHappyPlaceItem(
    private val happyPlaceRepository: HappyPlaceRepository
) {

    @Throws(InvalidHappyPlaceException::class)
    suspend operator fun invoke(happyPlaceItem: HappyPlaceItem) {
        if (happyPlaceItem.photoBytes == null) {
            throw InvalidHappyPlaceException("Image is empty.")
        } else if (happyPlaceItem.title.isEmpty()) {
            throw InvalidHappyPlaceException("Title is empty.")
        } else if (happyPlaceItem.description.isEmpty()) {
            throw InvalidHappyPlaceException("Description is empty.")
        } else if (happyPlaceItem.date.toString().isEmpty()) {
            throw InvalidHappyPlaceException("Date is empty.")
        } else if (happyPlaceItem.location.isEmpty()) {
            throw InvalidHappyPlaceException("Location is empty.")
        }

        happyPlaceRepository.upsertHappyPlace(happyPlaceItem)
    }
}
