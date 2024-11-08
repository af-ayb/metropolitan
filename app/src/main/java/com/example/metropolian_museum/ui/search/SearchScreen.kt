package com.example.metropolian_museum.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.metropolian_museum.R
import com.example.metropolian_museum.data.model.Objects
import com.example.metropolian_museum.ui.search.state.SearchScreenState
import com.example.metropolian_museum.ui.search.state.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onIdClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val screenState = viewModel.uiState.collectAsState()
    val keywordState = viewModel.keyword.collectAsState()
    SearchScreen(
        keyword = keywordState.value,
        state = screenState.value,
        event = viewModel::updateKeyword,
        onIdClick = onIdClick,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
    )


}

@Composable
private fun SearchScreen(
    keyword: String,
    state: SearchScreenState,
    event: (String) -> Unit,
    onIdClick: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ){
        SearchTextField(
            value = keyword,
            onValueChange = event,
            modifier = Modifier
        )
        when(state){
            is SearchScreenState.Empty ->{
                Text(
                    text = stringResource(R.string.empty)
                )
            }
            is SearchScreenState.Loading -> {
                Image(
                    modifier = modifier.size(dimensionResource(R.dimen.primary_image_size)),
                    painter = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.loading)
                )
            }
            is SearchScreenState.Error -> {
                Text (
                    text = stringResource(R.string.error, state.message)
                )
            }
            is SearchScreenState.Success -> {
                ArtsList(state.objects, onIdClick = onIdClick)
            }
        }
    }
}

@Composable
private fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = value,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = null
            )
        },
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(stringResource(R.string.search)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ArtsList(
    objects: Objects,
    onIdClick: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    val arts = objects.objectsIds?.map (Int::toString) ?: emptyList()
    LazyVerticalGrid (
        columns = GridCells.Adaptive(dimensionResource(R.dimen.grid_cell_size)),
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.padding_medium)),
    ){
        items(
            items = arts,
            key = {art -> art}
        ){art ->
            ArtIdCard(
                artId = art,
                modifier = modifier.fillMaxWidth(),
                onIdClick = onIdClick
            )
        }
    }
}

@Composable
private fun ArtIdCard(
    artId: String,
    onIdClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
        onClick = {onIdClick(artId)}
    ){
        Text(
            text = artId,
            modifier = modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)),
            textAlign = TextAlign.Center
        )
    }
}
