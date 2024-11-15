package com.example.metropolian_museum.ui.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.metropolian_museum.ui.search.state.SearchScreenState

class SearchScreenPreviewProvider : PreviewParameterProvider<SearchScreenState> {

    override val values = sequenceOf(
        SearchScreenState.Empty,
        SearchScreenState.Loading,
        SearchScreenState.Success(
           //"a",
            SearchPreviewData.artList,
        ),
        SearchScreenState.Error("No data")
    )
}