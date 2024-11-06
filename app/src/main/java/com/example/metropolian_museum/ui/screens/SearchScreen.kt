package com.example.metropolian_museum.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    searchState: SearchState,
    event: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
//    when(searchState){
//        is ArtsUiState.Loading -> null
//        is ArtsUiState.Success ->
            SearchScreen(
                state = searchState,
                event = event,
                modifier = modifier
                    .padding(top = contentPadding.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            )
//        else -> null

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    Column (
        modifier = modifier
            .fillMaxSize()
    ){
        TextField(
            value = state.searchQuery,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search, contentDescription = null
                )
            },
            onValueChange = {
                event(SearchEvent.UpdateSearchQuery(it))
                event(SearchEvent.SearchArts)
            },
            label = { Text("Search") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        ArtsList(
            arts = state.arts?.objectsIds?.map(Int::toString) ?: emptyList()
        )
    }
}

@Composable
private fun ArtsList(
    arts: List<String>,
    modifier: Modifier = Modifier,
){
    LazyVerticalGrid (
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier
            .padding(top = 16.dp),
    ){
        items(
            items = arts,
            key = {art -> art}
        ){art ->
            ArtIdCard(artId = art, modifier = modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ArtIdCard(
    artId: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(4.dp)
    ){
        Text(
            text = artId,
            modifier = modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}
