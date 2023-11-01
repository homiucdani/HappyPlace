package com.example.happyplace.feature_happy_place.happy_place_data.permission

import com.example.happyplace.feature_happy_place.happy_place_domain.permission.PermissionProvider

class CameraPermissionProviderImpl : PermissionProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if (!isPermanentlyDenied) {
            "In order to access your camera automatically, you need to allow the permission."
        } else {
            "It looks like you permanently denied the camera permission, in order to get a photo with the camera please allow the permission."
        }
    }
}