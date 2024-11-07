package com.example.metropolian_museum.ui.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.metropolian_museum.R
import com.example.metropolian_museum.network.Objects

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    contentPaddingValues: PaddingValues = PaddingValues(0.dp)
){
    val screenState = viewModel.uiState.collectAsState()
    val keywordState = viewModel.keyword.collectAsState()
    SearchScreen(
        keyword = keywordState.value,
        state = screenState.value,
        event = viewModel::updateKeyword,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = contentPaddingValues.calculateTopPadding())
            .fillMaxSize()
    )


}

@Composable
private fun SearchScreen(
    keyword: String,
    state: SearchScreenState,
    event: (String) -> Unit,
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
                    text = "Enter the keyword to find the art!"
                )
            }
            is SearchScreenState.Loading -> {
                Image(
                    modifier = modifier.size(200.dp),
                    painter = painterResource(R.drawable.loading_img),
                    contentDescription = "Loading"
                )
            }
            is SearchScreenState.Error -> {
                Text (
                    text = "Error: ${state.message}"
                )
            }
            is SearchScreenState.Success -> {
                ArtsList(state.objects)
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
        label = { Text("Search") },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ArtsList(
    objects: Objects,
    modifier: Modifier = Modifier,
){
    val arts = objects.objectsIds?.map (Int::toString) ?: emptyList()
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
