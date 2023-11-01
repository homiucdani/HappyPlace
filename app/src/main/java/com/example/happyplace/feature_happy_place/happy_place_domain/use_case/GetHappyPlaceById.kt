package com.example.happyplace.feature_happy_place.happy_place_domain.use_case

import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceRepository

class GetHappyPlaceById(
    private val happyPlaceRepository: HappyPlaceRepository
) {

    suspend operator fun invoke(id: Int): HappyPlaceItem {
        return happyPlaceRepository.getHappyPlaceById(id)
    }
}