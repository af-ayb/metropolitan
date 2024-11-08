package com.example.metropolian_museum.ui.details.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metropolian_museum.ui.ScreenRoute
import com.example.metropolian_museum.data.repository.ArtsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val artsRepository: ArtsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow<ArtDetailScreenState>(ArtDetailScreenState.Loading)
    val uiState: StateFlow<ArtDetailScreenState> = _uiState

    val id = ScreenRoute.DetailsScreenRoute.from(savedStateHandle).id

    init{
        getArt()
    }

    fun getArt(){
        _uiState.value = ArtDetailScreenState.Loading
        viewModelScope.launch{

            _uiState.value = try{
                ArtDetailScreenState.Success(artsRepository.getArtById(id))
            }catch (e: IOException){
                ArtDetailScreenState.Error
            }
        }
    }

}