package com.example.metropolian_museum.ui.details.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.metropolian_museum.ui.details.state.ArtDetailScreenState

internal class DetailsScreenPreviewProvider : PreviewParameterProvider<ArtDetailScreenState>{

    override val values = sequenceOf (
        ArtDetailScreenState.Loading,
        ArtDetailScreenState.Error,
        ArtDetailScreenState.Success(
            DetailsPreviewData.artWithFullDescription
        )
    )

}