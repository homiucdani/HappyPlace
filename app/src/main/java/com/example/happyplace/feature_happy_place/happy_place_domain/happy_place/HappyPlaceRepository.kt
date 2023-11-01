package com.example.happyplace.feature_happy_place.happy_place_domain.happy_place

import kotlinx.coroutines.flow.Flow

interface HappyPlaceRepository {

    suspend fun upsertHappyPlace(happyPlaceItem: HappyPlaceItem)

    suspend fun deleteHappyPlace(id:Int)

    suspend fun getHappyPlaceById(id: Int): HappyPlaceItem

    fun getHappyPlaces(): Flow<List<HappyPlaceItem>>
}