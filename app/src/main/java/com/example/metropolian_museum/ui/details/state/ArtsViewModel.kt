package com.example.metropolian_museum.ui.details.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.usecase.GetArtDetailsUseCase
import com.example.metropolian_museum.domain.usecase.UpdateFavoriteUseCase
import com.example.metropolian_museum.ui.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val getArtDetailsUseCase: GetArtDetailsUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val artId = ScreenRoute.DetailsScreenRoute.from(savedStateHandle).id

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _uiState: StateFlow<ArtDetailScreenState> = getArtDetailsUseCase(artId)
        .mapLatest{
            ArtDetailScreenState.Loading
            try{
                ArtDetailScreenState.Success(it)
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

    fun updateFavorite(artDetails: ArtDetails){
        updateFavoriteUseCase(artDetails)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}