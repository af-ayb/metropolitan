package com.example.metropolian_museum.ui.search.state

import androidx.lifecycle.ViewModel
import com.example.metropolian_museum.data.repository.ArtsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artsRepository: ArtsRepository,
): ViewModel(){

    // for simple cases, not preferred
    // mutableStateOf - for 1 Composable
//    var uiState: SearchScreenState by mutableStateOf(SearchScreenState.Empty)
//        private set

    // complex state, MVVM, unidirectional data flow
    private val _uiState = MutableStateFlow<SearchScreenState>(SearchScreenState.Empty)
    val uiState: StateFlow<SearchScreenState> = _uiState

    // mutableStateFlow - for more composables
    val keyword = MutableStateFlow("")

    init {
        keyword
            .debounce(500L)
            .onEach { search(it) }
            .launchIn(viewModelScope)
    }

    private fun search(query: String){
        if (query == ""){
            _uiState.value = SearchScreenState.Empty
        }
        else{
            _uiState.value = SearchScreenState.Loading

            viewModelScope.launch{
                try{
                    _uiState.value = SearchScreenState.Success(artsRepository.searchNews(query))
                } catch (e: IOException){
                    _uiState.value = SearchScreenState.Error(e.message ?: "Error")
                }
            }
        }

    }

    fun updateKeyword(new: String){
        keyword.value = new
    }

    fun navigateToArt(id: Int){

    }
}