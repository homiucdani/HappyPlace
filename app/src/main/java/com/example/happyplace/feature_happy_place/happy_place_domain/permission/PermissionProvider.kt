package com.example.happyplace.feature_happy_place.happy_place_domain.permission

interface PermissionProvider {
    fun getDescription(isPermanentlyDenied: Boolean): String
}