package com.example.metropolian_museum.ui.details.preview

import com.example.metropolian_museum.domain.model.ArtDetails

internal object DetailsPreviewData {
    val artDetailsWithFullDescription =
        ArtDetails(
            objectId = 122,
            primaryImage = "https://images.metmuseum.org/CRDImages/ep/original/DT1567.jpg",
            additionalImages = listOf(
                "https://images.metmuseum.org/CRDImages/as/original/DP251138.jpg",
                "https://images.metmuseum.org/CRDImages/as/original/DP251120.jpg"
            ),
            title = "Quail and Millet",
            department = "Asian Art",
            culture = "Japan",
            period = "Edo period (1615–1868)",
            artistRole = "Artist",
            artistDisplayName = "Kiyohara Yukinobu",
            objectDate = "late 17th century",
        )

    val artDetailsWithNullFields =
        ArtDetails(
            objectId = 122,
            primaryImage = null,
            additionalImages = null,
            title = "Title",
            department = "Department",
            culture = "Culture",
            period = null,
            artistRole = null,
            artistDisplayName = "Artist",
            objectDate = "1777 year"
        )
}