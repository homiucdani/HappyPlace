package com.example.happyplace.feature_happy_place.happy_place_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HappyPlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val location: String,
    val imagePath: String?,
    val latitude: Double,
    val longitude: Double
)
