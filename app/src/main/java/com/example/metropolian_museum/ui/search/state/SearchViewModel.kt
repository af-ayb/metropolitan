package com.example.metropolian_museum.ui.search.state

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.usecase.GetArtIdListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artsRepository: ArtsRepository,
    getArtIdListUseCase: GetArtIdListUseCase,
): ViewModel(){
    private val _keyword = MutableStateFlow<String>("")
    val keyword = _keyword.asStateFlow()

    // mapLatest - when the user types a new search query, it cancels any ongoing search request
    // and starts a new one, making the UI responsive and up-to-date.
    // This ensures that if the user changes the query rapidly,
    // only the latest request is processed, and previous requests are discarded.
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _uiState: StateFlow<SearchScreenState> = _keyword
        .mapLatest {
            getArtIdListUseCase(it)
//            artsRepository.getArtsFlow(it)
//                artsRepository.merge()
//            artsRepository.getArtsFlowLoading(it)
//                .mapLatest  {
//                    SearchScreenState.Success(it.)
//                }
//            artsRepository.getArtsFlow(it)
                .mapLatest {
                    SearchScreenState.Loading
                    try{
                        SearchScreenState.Success(it)
                    }catch (e: IOException){
                        e.printStackTrace()
                        SearchScreenState.Error(e.message.toString())
                    }
//                    when(it){
//                        LoadingEvent.Loading -> SearchScreenState.Loading
//                        is LoadingEvent.Error -> SearchScreenState.Error(it.reason)
//                        is LoadingEvent.Success -> SearchScreenState.Success(it.data)
//                    }
                }
        }

        .flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SearchScreenState.Empty
        )
    val uiState = _uiState

    fun updateKeyword(new: String){
        _keyword.value = new
    }
}