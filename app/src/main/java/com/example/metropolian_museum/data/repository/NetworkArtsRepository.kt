package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.remote.ArtsApiService
import com.example.metropolian_museum.data.model.api.ArtApi
import com.example.metropolian_museum.data.model.api.ObjectsApi
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.Art
import com.example.metropolian_museum.domain.model.Objects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkArtsRepository @Inject constructor(
    private val artsApiService: ArtsApiService
): ArtsRepository{
    override suspend fun getArtById(id: String): Art {
        return artsApiService.getObjectById(id).asDomain()
    }

    // suspend -> return Flow
    override suspend fun searchArts(searchQuery: String): Objects {
        return artsApiService.searchObjects(searchQuery).asDomain()
    }

    override fun searchArtsFlow(searchQuery: String): Flow<LoadingEvent<Objects>>{
        return flow {
            emit(LoadingEvent.Loading)
            try {
                val objects = artsApiService.searchObjects(searchQuery).asDomain()
                emit(LoadingEvent.Success(objects))
            } catch (e: Exception) {
                emit(LoadingEvent.Error(e.message.toString()))
            }
        }
    }
}

fun ArtApi.asDomain() = Art(
    objectId = objectId,
    primaryImage = if (primaryImage=="") null else primaryImage,
    additionalImages = if (additionalImages.isEmpty()) null else additionalImages,
    title = if (title == "") null else title,
    department = if (department == "") null else department,
    culture = if (culture == "") null else culture,
    period = if (period == "") null else period,
    artistRole = if (artistRole == "") null else artistRole,
    artistDisplayName = if (artistDisplayName == "") null else artistDisplayName,
    objectDate = if (objectDate == "") null else objectDate
)

fun ObjectsApi.asDomain() = Objects(
    total = total,
    objectsIds = objectsIds
)