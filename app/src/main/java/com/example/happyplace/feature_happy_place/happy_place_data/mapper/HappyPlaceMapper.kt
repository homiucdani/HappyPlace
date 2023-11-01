package com.example.happyplace.feature_happy_place.happy_place_data.mapper

import com.example.happyplace.feature_happy_place.happy_place_data.local.entity.HappyPlaceEntity
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.LatitudeLongitude
import com.example.happyplace.feature_happy_place.happy_place_domain.storage.ImageStorage
import java.time.LocalDate

suspend fun HappyPlaceItem.toHappyPlaceEntity(imageStorage: ImageStorage): HappyPlaceEntity {

    return HappyPlaceEntity(
        id = id,
        title = title,
        description = description,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        location = location,
        imagePath = photoBytes?.let { imageStorage.saveImage(it) },
        latitude = this.latitudeLongitude.latitude,
        longitude = this.latitudeLongitude.longitude
    )
}

suspend fun HappyPlaceEntity.toHappyPlaceItem(imageStorage: ImageStorage): HappyPlaceItem {

    return HappyPlaceItem(
        id = id,
        title = title,
        description = description,
        date = LocalDate.of(this.year, this.month, this.dayOfMonth),
        location = location,
        photoBytes = imagePath?.let { imageStorage.getImage(it) },
        latitudeLongitude = LatitudeLongitude(
            latitude = this.latitude,
            longitude = this.longitude
        )
    )
}