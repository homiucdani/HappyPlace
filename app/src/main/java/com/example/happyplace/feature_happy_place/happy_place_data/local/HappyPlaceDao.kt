package com.example.happyplace.feature_happy_place.happy_place_data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.happyplace.feature_happy_place.happy_place_data.local.entity.HappyPlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HappyPlaceDao {

    @Upsert
    suspend fun upsertHappyPlace(happyPlaceEntity: HappyPlaceEntity)

    @Query("DELETE FROM happyplaceentity WHERE id = :id")
    suspend fun deleteHappyPlace(id: Int)

    @Query("SELECT * FROM happyplaceentity WHERE id = :id")
    suspend fun getHappyPlaceById(id: Int): HappyPlaceEntity

    @Query("SELECT * FROM happyplaceentity")
    fun getHappyPlaces(): Flow<List<HappyPlaceEntity>>
}