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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.metropolian_museum.R
import com.example.metropolian_museum.network.Objects
import com.example.metropolian_museum.ui.ArtsUiState
import com.example.metropolian_museum.ui.theme.MetropolianMuseumTheme

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
                    .padding(16.dp),
                contentPadding = contentPadding
            )
//        else -> null

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
//    arts: Objects,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    SearchBar(
        query = state.searchQuery,
        onQueryChange = {event(SearchEvent.UpdateSearchQuery(it))},
        onSearch = {event(SearchEvent.SearchArts)},
        active = state.isSearching,
        onActiveChange = {event(SearchEvent.SearchArts)},
        modifier = modifier.fillMaxWidth().padding(40.dp)
    )
    {
        ArtsList(
            arts = state.arts?.objectsIds?.map (Int::toString ) ?: emptyList()
        )
    }
}

@Composable
fun SearchTextField(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    TextField(
        value = value,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = null
            )
        },
        onValueChange = onValueChange,
        label = {Text(stringResource(label))},
        singleLine = true,
        modifier = modifier
            .padding(top = contentPadding.calculateTopPadding())
    )
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

//@Preview
//@Composable
//fun SearchScreenPreview(){
//    MetropolianMuseumTheme {
//        val mockData = Objects(2, listOf(1233, 1234))
//        SearchScreen(mockData, Modifier.fillMaxSize())
//    }
//}