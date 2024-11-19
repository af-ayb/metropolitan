package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.local.dao.ArtDao
import com.example.metropolian_museum.data.model.api.ArtDetailsApi
import com.example.metropolian_museum.data.model.db.ArtEntity
import com.example.metropolian_museum.data.remote.ArtsApiService
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtsRepositoryImpl @Inject constructor(
    private val artsApiService: ArtsApiService,
    private val dao: ArtDao,
) : ArtsRepository {

    private fun getArtDetailsFromApiById(id: Int): Flow<ArtDetails> = flow {
        emit(artsApiService.getObjectById(id.toString()).asDomain())
    }

    private fun getArtsFlowLoadingEvent(searchQuery: String): Flow<LoadingEvent<List<ArtId>>> {
        return flow {
            emit(LoadingEvent.Loading)
            try {
                val objects = artsApiService.searchObjects(searchQuery).objectsIds
                    ?.map { it.asArtIdDomain() }
                    ?: emptyList()
                emit(LoadingEvent.Success(objects))
            } catch (e: Exception) {
                emit(LoadingEvent.Error(e.message.toString()))
            }
        }
    }

    private fun getFavorites(): Flow<List<ArtId>> = dao.getFavorites()
        .map { it.map(ArtEntity::asDomain) }


    private fun getFavoriteById(id: Int): Flow<ArtId?> = dao.getFavorite(id)
        .map { it?.asDomain() }


    override fun updateFavorite(artDetails: ArtDetails): Flow<Boolean> = flowOf(artDetails)
        .map { it.asEntity().copy(favorite = !artDetails.isFavorite) }
        .map { dao.insert(it) }
        .map { it != 0L }


    override fun getArtsListWithFavs(searchQuery: String): Flow<LoadingEvent<List<ArtId>>> =
        getArtsFlowLoadingEvent(searchQuery)
            .combine(
                getFavorites()
            ) { apiList, dbList ->
                when (apiList) {
                    LoadingEvent.Loading -> LoadingEvent.Loading
                    is LoadingEvent.Error -> LoadingEvent.Error(apiList.reason)
                    is LoadingEvent.Success -> LoadingEvent.Success(
                        apiList.data
                            .map { art ->
                                art.copy(
                                    isFavorite = dbList.find { it.artId == art.artId }?.isFavorite
                                        ?: false
                                )
                            }
                    )
                }
            }

    override fun getArtDetailsById(id: Int): Flow<ArtDetails> = getArtDetailsFromApiById(id)
        .combine(
            getFavoriteById(id)
        ) { api: ArtDetails, db: ArtId? ->
            api.copy(isFavorite = db?.isFavorite ?: false)
        }
}

fun ArtDetailsApi.asDomain() = ArtDetails(
    objectId = objectId,
    primaryImage = if (primaryImage == "") null else primaryImage,
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