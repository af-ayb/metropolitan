package com.example.metropolian_museum.ui.search.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metropolian_museum.data.repository.ArtsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.io.IOException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artsRepository: ArtsRepository,
): ViewModel(){
    // avoid reactive streams (StateFlow) to represent TextField
    var keyword by mutableStateOf("")
        private set

    private val _uiState: StateFlow<SearchScreenState> =
        snapshotFlow { keyword }
            .mapLatest {
                if (it.isBlank()) SearchScreenState.Empty
                else{
                    SearchScreenState.Loading
                    try{
                        SearchScreenState.Success(it, artsRepository.searchArts(it))

                    } catch (e: IOException){
                        SearchScreenState.Error(e.message ?: "Error")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SearchScreenState.Empty
            )
    val uiState = _uiState

    fun updateKeyword(new: String){
        keyword = new
    }
}