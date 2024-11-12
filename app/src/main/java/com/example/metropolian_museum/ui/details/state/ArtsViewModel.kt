package com.example.metropolian_museum.ui.details.state

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metropolian_museum.ui.ScreenRoute
import com.example.metropolian_museum.data.repository.ArtsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val artsRepository: ArtsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val id = ScreenRoute.DetailsScreenRoute.from(savedStateHandle).id

    private val _uiState: StateFlow<ArtDetailScreenState> =
            snapshotFlow{id}
                .mapLatest {
                    ArtDetailScreenState.Loading
                    try{
                        ArtDetailScreenState.Success(artsRepository.getArtById(id))
                    }catch (e: Exception){
                        ArtDetailScreenState.Error(e.message.toString())
                    }
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = ArtDetailScreenState.Loading
                )
    val uiState = _uiState
}