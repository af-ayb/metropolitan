package com.example.metropolian_museum.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.metropolian_museum.R
import com.example.metropolian_museum.domain.model.Art
import com.example.metropolian_museum.ui.AppBar
import com.example.metropolian_museum.ui.details.preview.DetailsScreenPreviewProvider
import com.example.metropolian_museum.ui.details.state.ArtDetailScreenState
import com.example.metropolian_museum.ui.details.state.ArtDetailsViewModel
import com.example.metropolian_museum.ui.theme.MetropolianMuseumTheme
import kotlin.String

@Composable
fun ArtDetailsScreen(
    viewModel: ArtDetailsViewModel = hiltViewModel(),
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
){
    val screenState = viewModel.uiState.collectAsState()
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
            DetailsScreen(screenState.value, modifier = Modifier.padding(top = innerPadding.calculateTopPadding()).fillMaxSize())
        }
    }
}

@Composable
private fun DetailsScreen(
    state: ArtDetailScreenState,
    modifier: Modifier = Modifier
){
    when(state){
        is ArtDetailScreenState.Loading -> {
            DetailsScreenLoading(modifier)
        }
        is ArtDetailScreenState.Error -> {
            DetailsScreenError(modifier)
        }
        is ArtDetailScreenState.Success -> {
            DetailsLayout(art = state.art, modifier = modifier)
        }
    }
}

@Composable
private fun DetailsScreenLoading(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
){
    Image(
        modifier = Modifier
            .size(dimensionResource(R.dimen.primary_image_size)),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
private fun DetailsScreenError(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
){
    Text (
        text = stringResource(R.string.error)
    )
}

@Composable
fun ArtImage(
    picUrl: String?,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
    ){
        if (picUrl.isNullOrEmpty()){
            Image(
                painter = painterResource(R.drawable.no_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.secondary_image_size))
                    .fillMaxWidth()
            )
        }
        else{
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(picUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.primary_image_size))
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun DetailsLayout(
    art: Art,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ArtImage(
            picUrl = art.primaryImage
        )
        Column(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_medium))
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextOrEmpty(
                text = art.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            TextOrEmpty(
                text = art.objectDate,
                style = MaterialTheme.typography.bodyMedium,
            )
            TextOrEmpty(
                text = art.department,
                style = MaterialTheme.typography.labelLarge
            )
            MultipleTextsOrEmpty(
                texts = listOf(art.culture, art.period),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            MultipleTextsOrEmpty(
                texts = listOf(art.artistRole, art.artistDisplayName),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = art.objectId.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        art.additionalImages?.let{
            ImagesScroller(art.additionalImages,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
        }
    }
}

@Composable
fun MultipleTextsOrEmpty(
    texts: List<String?>,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Center
){
    val nonEmptyTexts = texts.filter{ it != null && it.isNotEmpty() }
    TextOrEmpty(
        text = nonEmptyTexts.joinToString(", "),
        style = style,
        textAlign = textAlign
    )
}

@Composable
fun TextOrEmpty(
    text: String?,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Center
){
    if (!text.isNullOrEmpty()){
        Text(
            text = text,
            style = style,
            textAlign = textAlign
        )
    }
}

@Composable
fun ImagesScroller(imagesUrls: List<String>, modifier: Modifier = Modifier){
    val pagerState = rememberPagerState (pageCount = {imagesUrls.size})
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState
        ) { index ->
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imagesUrls[index])
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.secondary_image_size))
                    .fillMaxSize()
            )
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }

}

@Preview
@Composable
fun DetailsScreenPreview(
    @PreviewParameter(DetailsScreenPreviewProvider::class) state: ArtDetailScreenState
){
    MetropolianMuseumTheme {
        DetailsScreen(
            state = state
        )
    }
}