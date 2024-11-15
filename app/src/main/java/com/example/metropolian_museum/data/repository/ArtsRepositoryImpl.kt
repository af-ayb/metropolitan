package com.example.metropolian_museum.data.repository

import android.annotation.SuppressLint
import com.example.metropolian_museum.data.local.dao.ArtDao
import com.example.metropolian_museum.data.remote.ArtsApiService
import com.example.metropolian_museum.data.model.api.ArtDetailsApi
import com.example.metropolian_museum.data.model.db.ArtEntity
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtsRepositoryImpl @Inject constructor(
    private val artsApiService: ArtsApiService,
    private val dao: ArtDao,
): ArtsRepository{
    override fun getArtDetailsById(id: Int): Flow<ArtDetails> {
        return flow{emit(artsApiService.getObjectById(id.toString()).asDomain())}
    }

    override fun getArtsFlow(searchQuery: String): Flow<List<ArtId>>{
        return flow{
            val objects = artsApiService.searchObjects(searchQuery).objectsIds
                ?.map { it.asArtIdDomain() }
                ?: emptyList()
            emit(objects)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getArtsFlowLoading(searchQuery: String): Flow<LoadingEvent<List<ArtId>>>{
//        return flow{
//            emit(LoadingEvent.Loading)
//            try {
//                val objects = artsApiService.searchObjects(searchQuery).objectsIds
//                        ?.map { it.asArtIdDomain() }
//                        ?: emptyList()
//                    emit(LoadingEvent.Success(objects))
//            }catch (e: Exception) {
//                emit(LoadingEvent.Error(e.message.toString()))
//            }
//        }
        return flow {
            // Emit the Loading state initially
            emit(LoadingEvent.Loading)

            try {
                // Get the list of favorites (this is a Flow that you'll collect)
                val favouriteList = dao.getFavourites().first() // Collect the first (and only) emission

                // Call the suspend function searchObjects() to get the search result (not a Flow)
                val apiResponse = artsApiService.searchObjects(searchQuery)

                // Map the favourite list to a set of favorite artIds
                val favIds = favouriteList.map{it.artId}.toSet()

                // Map the API response to ArtId objects and mark them as favorites if their ID is in favIds
                val objects = apiResponse.objectsIds
                    ?.map { it.asArtIdDomain() } // Map the response to ArtIdDomain objects
                    ?.onEach { artId ->
                        // Mark art as favorite if it exists in favIds
                        artId.isFavorite = artId.artId in favIds
                    }
                    ?: emptyList() // In case objectsIds is null, return an empty list

                // Emit the final list of ArtId objects wrapped in a Success event
                emit(LoadingEvent.Success(objects))
            } catch (e: Exception) {
                // Emit an error event in case of an exception
                emit(LoadingEvent.Error(e.message.toString()))
            }
        }
    }

    override fun getFavoritesIds(): Flow<List<Int>> {
        return flow{
            dao.getFavourites()
                .map{ ArtEntity::artId }
        }
    }

    override fun getFavs(): Flow<List<ArtId>> =  flow{
            dao.getFavourites()
        }

    override fun getFavorite(id: Int): Flow<ArtId?> {
        return dao.getFavorite(id)
            .map{it?.asDomain()}
    }


    override fun merge(): Flow<List<ArtId>>{
        return flow{
            dao.insert(ArtEntity(500580))
            combine(getArtsFlow("op"), getFavs()){
                api, db ->
                val favs = db.map{it.artId}.toSet()
                api
                    .map {
                        it.copy(isFavorite = it.artId in favs)
                    }
            }
        }
    }

    override fun updateFavorite(artId: ArtId): Flow<Boolean>{
        return flowOf(artId)
            .map{it.asEntity().copy(favorite = !artId.isFavorite)}
            .map{dao.insert(it)}
            .map{it != 0L}
//        return dao.getCountOfArtInFavorite(artId)
//            .map {
//                if (it > 0) {
//                    dao.getFavouriteByArtId(artId).let {
//                        dao.delete(it)
//                        Log.d("Repo", "${artId} deleted")
//                        false
//                    }
//                } else {
//                    dao.insert(artId.asEntity())
//                    true
//                }
//            }
//            .catch{e -> emit(false)}
    }

//    override fun isFavorite(artId: Int): Flow<Boolean> {
//        return dao.getCountOfArtInFavorite(artId)
//            .map { it != 0 }
//    }
}

//fun Int.asEntity() = ArtEntity(
//    artId = this
//)

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

fun ArtId.asEntity() = ArtEntity(
    artId = artId
)

//
//fun ArtListApi.asDomain() = ArtList(
//    total = total,
//    objectsIds = objectsIds
//)

fun Int.asArtIdDomain() = ArtId(
    artId = this,
    isFavorite = false
)

fun ArtEntity.asDomain() = ArtId(
    artId = artId,
    isFavorite = favorite
)