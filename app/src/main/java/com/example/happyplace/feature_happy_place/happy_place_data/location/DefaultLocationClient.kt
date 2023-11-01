package com.example.happyplace.feature_happy_place.happy_place_data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.example.happyplace.feature_happy_place.happy_place_data.util.hasLocationPermission
import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.util.PlacesResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient,
    private val placesClient: PlacesClient
) : LocationClient {

    private val token = AutocompleteSessionToken.newInstance()

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocationOnce(location: (Location) -> Unit) {

        if (!context.hasLocationPermission()) {
            throw LocationClient.LocationException("Missing location permission.")
        }

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            throw LocationClient.LocationException("Gps or Network is not enabled.")
        }


        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
            .setMaxUpdates(1)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let { receivedLocation ->
                    location(receivedLocation)
                }
            }
        }

        client.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun searchLocationByName(query: String): Flow<PlacesResult> {
        return callbackFlow {

            if (!context.hasLocationPermission()) {
                throw LocationClient.LocationException("Missing location permission.")
            }

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationClient.LocationException("Gps or Network is not enabled.")
            }

            val request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(token)
                .setQuery(query)
                .build()

            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                trySend(PlacesResult.Success(response.autocompletePredictions))
            }.addOnFailureListener {
                trySend(PlacesResult.Error(it.message.toString()))
            }

            awaitClose {}
        }
    }
}


