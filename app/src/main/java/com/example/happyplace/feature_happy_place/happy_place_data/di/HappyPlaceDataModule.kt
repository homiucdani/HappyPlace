package com.example.happyplace.feature_happy_place.happy_place_data.di

import android.app.Application
import android.location.Geocoder
import androidx.room.Room
import com.example.happyplace.feature_happy_place.happy_place_data.local.HappyPlaceDao
import com.example.happyplace.feature_happy_place.happy_place_data.local.HappyPlaceDatabase
import com.example.happyplace.feature_happy_place.happy_place_data.local.happy_place.HappyPlaceRepositoryImpl
import com.example.happyplace.feature_happy_place.happy_place_data.local.storage.ImageStorageImpl
import com.example.happyplace.feature_happy_place.happy_place_data.location.DefaultLocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceRepository
import com.example.happyplace.feature_happy_place.happy_place_domain.location.LocationClient
import com.example.happyplace.feature_happy_place.happy_place_domain.storage.ImageStorage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HappyPlaceDataModule {

    @Provides
    @Singleton
    fun providesHappyPlaceDatabase(app: Application): HappyPlaceDatabase {
        return Room.databaseBuilder(
            app,
            HappyPlaceDatabase::class.java,
            "happy_place.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesHappyPlaceDao(db: HappyPlaceDatabase): HappyPlaceDao {
        return db.happyPlaceDao
    }

    @Provides
    @Singleton
    fun providesHappyPlaceRepository(dao: HappyPlaceDao, imageStorage: ImageStorage): HappyPlaceRepository {
        return HappyPlaceRepositoryImpl(dao, imageStorage)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app.applicationContext)
    }

    @Provides
    @Singleton
    fun providesPlacesClient(app: Application): PlacesClient {
        Places.initialize(app, "AIzaSyD16bvslksJCcAHv6qq1cqaO3c6mVqvosQ")
        return Places.createClient(app)
    }


    @Provides
    @Singleton
    fun providesLocationClient(
        app: Application,
        client: FusedLocationProviderClient,
        placesClient: PlacesClient
    ): LocationClient {
        return DefaultLocationClient(
            context = app.applicationContext,
            client = client,
            placesClient = placesClient
        )
    }

    @Provides
    @Singleton
    fun providesGeocoder(
        app: Application,
    ): Geocoder {
        return Geocoder(app)
    }

    @Provides
    @Singleton
    fun providesImageStorage(application: Application): ImageStorage {
        return ImageStorageImpl(application)
    }
}