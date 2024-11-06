package com.example.metropolian_museum.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.metropolian_museum.data.ArtsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    val artsRepository: ArtsRepository
): ViewModel(){

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }
            is SearchEvent.SearchArts ->{
                searchArts()
                _state.value = _state.value.copy(isSearching = !state.value.isSearching)
//                if (!_state.value.isSearching) {
//                    _state.value = state.value.copy(searchQuery = event.searchQuery)
//                }
            }
        }
    }

    private fun searchArts(){
        viewModelScope.launch{
            val arts = artsRepository.searchNews(
                searchQuery = state.value.searchQuery,
            )
            _state.value = _state.value.copy(arts = arts)
        }

    }


}