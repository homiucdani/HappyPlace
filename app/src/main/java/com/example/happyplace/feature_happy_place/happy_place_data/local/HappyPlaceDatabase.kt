package com.example.happyplace.feature_happy_place.happy_place_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.happyplace.feature_happy_place.happy_place_data.local.entity.HappyPlaceEntity

@Database(
    entities = [
        HappyPlaceEntity::class
    ],
    version = 1
)
abstract class HappyPlaceDatabase : RoomDatabase() {

    abstract val happyPlaceDao: HappyPlaceDao

}