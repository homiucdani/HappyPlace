package com.example.happyplace.feature_happy_place.happy_place_data.local.happy_place

import com.example.happyplace.feature_happy_place.happy_place_data.local.HappyPlaceDao
import com.example.happyplace.feature_happy_place.happy_place_data.mapper.toHappyPlaceEntity
import com.example.happyplace.feature_happy_place.happy_place_data.mapper.toHappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceItem
import com.example.happyplace.feature_happy_place.happy_place_domain.happy_place.HappyPlaceRepository
import com.example.happyplace.feature_happy_place.happy_place_domain.storage.ImageStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class HappyPlaceRepositoryImpl(
    private val dao: HappyPlaceDao,
    private val imageStorage: ImageStorage
) : HappyPlaceRepository {

    override suspend fun upsertHappyPlace(happyPlaceItem: HappyPlaceItem) {
        dao.upsertHappyPlace(happyPlaceItem.toHappyPlaceEntity(imageStorage))
    }

    override suspend fun deleteHappyPlace(id: Int) {
        dao.deleteHappyPlace(id)
    }

    override suspend fun getHappyPlaceById(id: Int): HappyPlaceItem {
        return dao.getHappyPlaceById(id).toHappyPlaceItem(imageStorage)
    }

    override fun getHappyPlaces(): Flow<List<HappyPlaceItem>> {
        return dao.getHappyPlaces().map { happyPlaces ->
            supervisorScope {
                happyPlaces.map {
                    async {
                        it.toHappyPlaceItem(imageStorage)
                    }
                }.map {
                    it.await()
                }
            }
        }
    }
}