package com.example.metropolian_museum.ui.search.preview

import com.example.metropolian_museum.domain.model.ArtId
import com.example.metropolian_museum.domain.model.ArtList

internal object SearchPreviewData {
    val artList1 = ArtList(
        total = 10,
        objectsIds = listOf(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10
        )
    )
    val artList = listOf(
        ArtId(1),
        ArtId(2),
        ArtId(3),
        ArtId(4),
        ArtId(5),
        ArtId(6),
        ArtId(7),
        ArtId(8),
        ArtId(9),
    )
}