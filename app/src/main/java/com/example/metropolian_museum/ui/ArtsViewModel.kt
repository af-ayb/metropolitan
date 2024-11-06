package com.example.metropolian_museum.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.metropolian_museum.MetropolianApplication
import com.example.metropolian_museum.data.ArtsRepository
import com.example.metropolian_museum.network.Objects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed interface ArtsUiState{
    data class Success(val arts: Objects) : ArtsUiState
    object Error: ArtsUiState
    object Loading: ArtsUiState
}

class ArtsViewModel(
    private val artsRepository: ArtsRepository
): ViewModel() {
    var artsUiState: ArtsUiState by mutableStateOf(ArtsUiState.Loading)
        private set

    init{
        getArts()
    }

    fun getArts(){
        viewModelScope.launch{
            artsUiState = ArtsUiState.Loading
            artsUiState = try{
                ArtsUiState.Success(artsRepository.getArtsById())
            }catch (e: IOException){
                ArtsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as MetropolianApplication)
                val artsRepository = application.container.artsRepository
                ArtsViewModel(artsRepository = artsRepository)
            }
        }
    }
}