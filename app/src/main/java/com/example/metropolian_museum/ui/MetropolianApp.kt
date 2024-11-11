package com.example.metropolian_museum.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metropolian_museum.ui.ScreenRoute.DetailsScreenRoute
import com.example.metropolian_museum.ui.ScreenRoute.SearchScreenRoute
import com.example.metropolian_museum.ui.details.ArtDetailsScreen
import com.example.metropolian_museum.ui.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetropolianMuseumApp(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SearchScreenRoute
    ) {
        composable<SearchScreenRoute> {
            SearchScreen(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {navController.navigateUp()},
                onIdClick = {navController.navigate(DetailsScreenRoute(it))}
            )
        }
        composable<DetailsScreenRoute>{
            ArtDetailsScreen(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {navController.navigateUp()},
                )
        }
    }
}

