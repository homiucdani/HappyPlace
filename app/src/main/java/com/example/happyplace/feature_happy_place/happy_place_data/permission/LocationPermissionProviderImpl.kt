package com.example.happyplace.feature_happy_place.happy_place_data.permission

import com.example.happyplace.feature_happy_place.happy_place_domain.permission.PermissionProvider

class LocationPermissionProviderImpl : PermissionProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if (!isPermanentlyDenied) {
            "In order to access your location automatically, you need to allow the permission."
        } else {
            "It looks like you permanently denied the location permission, in order to get your location please allow the permission."
        }
    }
}