package com.example.metropolian_museum.domain.usecase

import android.util.Log
import androidx.room.Query
import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.di.IoDispatcher
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetArtIdListUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: ArtsRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(searchQuery: String): Flow<List<ArtId>> = repository.getArtsFlow(searchQuery)
        .combine(
            repository.getFavs()
        ){
            apiList, dbList ->
            apiList.map { art ->
                art.copy(isFavorite = dbList.find{it.artId == art.artId}?.isFavorite ?: false)
            }
//            when(apiList){
//                LoadingEvent.Loading -> LoadingEvent.Loading
//                is LoadingEvent.Error -> LoadingEvent.Error(apiList.reason)
//                is LoadingEvent.Success -> LoadingEvent.Success(
//                    apiList.data
////                        .map{ art ->
////                    Log.d("DB", art.toString())
////                        art.copy(isFavorite = art.artId in dbList.toSet())
////                    }
//                )
//            }
        }
//        .mapLatest {
//            when(it){
//                LoadingEvent.Loading -> LoadingEvent.Loading
//                is LoadingEvent.Error -> LoadingEvent.Error(it.reason)
//                is LoadingEvent.Success -> LoadingEvent.Success(
////                        .map{ art ->
////                    Log.d("DB", art.toString())
////                        art.copy(isFavorite = art.artId in dbList.toSet())
////                    }
//                )
//            }
//        }
        .flowOn(ioDispatcher)
}