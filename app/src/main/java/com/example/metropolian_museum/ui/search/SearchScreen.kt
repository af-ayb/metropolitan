package com.example.metropolian_museum.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.metropolian_museum.R
import com.example.metropolian_museum.domain.model.Objects
import com.example.metropolian_museum.ui.AppBar
import com.example.metropolian_museum.ui.search.preview.SearchScreenPreviewProvider
import com.example.metropolian_museum.ui.search.state.SearchScreenState
import com.example.metropolian_museum.ui.search.state.SearchViewModel
import com.example.metropolian_museum.ui.theme.MetropolianMuseumTheme
import kotlinx.coroutines.Dispatchers

// stateful version of the screen
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onIdClick: (String) -> Unit,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
){
    val screenState = viewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val keywordState = viewModel.keyword.collectAsState(Dispatchers.Main.immediate)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            SearchScreen(
                query = keywordState.value,
                state = screenState.value,
                event = viewModel::updateKeyword,
                onSearchClicked = {keyboardController?.hide()},
                onIdClick = onIdClick,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize()
            )
        }
    }
}

// stateless version
@Composable
private fun SearchScreen(
    query: String,
    state: SearchScreenState,
    event: (String) -> Unit,
    onIdClick: (String) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ){
        SearchTextField(
            value = query,
            onValueChange = event,
            onSearchClicked = onSearchClicked,
        )
        when(state){
            is SearchScreenState.Empty ->{
                SearchScreenEmpty(Modifier.fillMaxSize())
            }
            is SearchScreenState.Loading -> {
                SearchScreenLoading(Modifier.fillMaxSize())
            }
            is SearchScreenState.Error -> {
                SearchScreenError(
                    errorMessage = state.message,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is SearchScreenState.Success -> {
                ArtsList(state.objects, onIdClick = onIdClick)
            }
        }
    }
}

@Composable
private fun SearchScreenEmpty(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
){
    Text(
        text = stringResource(R.string.empty)
    )
}

@Composable
private fun SearchScreenLoading(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
){
    Image(
        modifier = modifier.size(dimensionResource(R.dimen.primary_image_size)),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
private fun SearchScreenError(
    errorMessage: String,
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
){
    Text (
        text = stringResource(R.string.error, errorMessage)
    )
}


@Composable
private fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {onSearchClicked()}),
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

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchScreenPreviewProvider::class) state: SearchScreenState
){
    MetropolianMuseumTheme {
        SearchScreen(
            query = "a",
            state = state,
            event = { },
            onSearchClicked = {},
            onIdClick = { },
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}