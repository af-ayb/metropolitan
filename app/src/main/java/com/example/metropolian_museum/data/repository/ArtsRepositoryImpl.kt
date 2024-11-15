package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.local.dao.ArtDao
import com.example.metropolian_museum.data.remote.ArtsApiService
import com.example.metropolian_museum.data.model.api.ArtDetailsApi
import com.example.metropolian_museum.data.model.db.ArtEntity
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtsRepositoryImpl @Inject constructor(
    private val artsApiService: ArtsApiService,
    private val dao: ArtDao,
): ArtsRepository{
    override fun getArtDetailsById(id: Int): Flow<ArtDetails> = flow{
        emit(artsApiService.getObjectById(id.toString()).asDomain())
    }

    override fun getArtsFlowLoadingEvent(searchQuery: String): Flow<LoadingEvent<List<ArtId>>>{
        return flow{
            emit(LoadingEvent.Loading)
            try {
                val objects = artsApiService.searchObjects(searchQuery).objectsIds
                        ?.map { it.asArtIdDomain() }
                        ?: emptyList()
                    emit(LoadingEvent.Success(objects))
            }catch (e: Exception) {
                emit(LoadingEvent.Error(e.message.toString()))
            }
        }
    }

    override fun getFavorites(): Flow<List<ArtId>> = dao.getFavorites()
        .map{it.map (ArtEntity::asDomain) }


    override fun getFavoriteById(id: Int): Flow<ArtId?> = dao.getFavorite(id)
        .map{it?.asDomain()}


    override fun updateFavorite(artDetails: ArtDetails): Flow<Boolean> = flowOf(artDetails)
            .map { it.asEntity().copy(favorite = !artDetails.isFavorite) }
            .map { dao.insert(it) }
            .map { it != 0L }

}

fun ArtDetailsApi.asDomain() = ArtDetails(
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

fun Int.asArtIdDomain() = ArtId(
    artId = this,
    isFavorite = false
)

fun ArtEntity.asDomain() = ArtId(
    artId = artId,
    isFavorite = favorite
)

fun ArtDetails.asEntity() = ArtEntity(
    artId = objectId,
    favorite = isFavorite,
)