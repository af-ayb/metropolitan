package com.example.metropolian_museum.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.metropolian_museum.network.Art
import com.example.metropolian_museum.ui.theme.MetropolianMuseumTheme

@Composable
fun ArtImage(
    picUrl: String,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(picUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
fun DetailsLayout(
    art: Art,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ) {
        ArtImage(
            picUrl = art.primaryImage
        )
        Text(
            text = art.title,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = art.objectId.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = art.department,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
fun DetailsLayoutPreview(){
    MetropolianMuseumTheme {

        Surface {
            DetailsLayout(
                Art(
                    objectId = 45734,
                    primaryImage = "https://images.metmuseum.org/CRDImages/as/original/DP251139.jpg",
                    additionalImages = listOf(
                        "https://images.metmuseum.org/CRDImages/as/original/DP251138.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP251120.jpg"
                    ),
                    title = "Quail and Millet",
                    department = "Asian Art",
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}