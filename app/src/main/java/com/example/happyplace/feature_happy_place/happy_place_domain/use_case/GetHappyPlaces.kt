package com.example.happyplace.feature_happy_place.happy_place_domain.use_case

import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceRepository
import kotlinx.coroutines.flow.Flow

class GetHappyPlaces(
    private val happyPlaceRepository: HappyPlaceRepository
) {

    operator fun invoke(): Flow<List<HappyPlaceItem>> {
        return happyPlaceRepository.getHappyPlaces()
    }
}