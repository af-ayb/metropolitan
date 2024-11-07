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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

sealed interface ArtsUiState{
    data class Success(val arts: Objects) : ArtsUiState
    object Error: ArtsUiState
    object Loading: ArtsUiState
}

@HiltViewModel
class ArtsViewModel @Inject constructor(
    private val artsRepository: ArtsRepository
): ViewModel() {
    var artsUiState: ArtsUiState by mutableStateOf(ArtsUiState.Loading)
        private set

//    init{
//        getArts()
//    }

//    fun getArts(){
//        viewModelScope.launch{
//            artsUiState = ArtsUiState.Loading
//            artsUiState = try{
//                ArtsUiState.Success(artsRepository.getArtsById())
//            }catch (e: IOException){
//                ArtsUiState.Error
//            }
//        }
//    }

}