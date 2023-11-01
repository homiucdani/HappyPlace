package com.example.happyplace.feature_happy_place.happy_place_domain.storage

interface ImageStorage {

    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(fileName: String): ByteArray
}