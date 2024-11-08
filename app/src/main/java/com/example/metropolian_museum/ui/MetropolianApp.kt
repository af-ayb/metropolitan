package com.example.metropolian_museum.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metropolian_museum.R
import com.example.metropolian_museum.ui.ScreenRoute.DetailsScreenRoute
import com.example.metropolian_museum.ui.ScreenRoute.SearchScreenRoute
import com.example.metropolian_museum.ui.details.ArtDetailsScreen
import com.example.metropolian_museum.ui.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetropolianMuseumApp(){
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {navController.navigateUp()}
            )
        }
    ){  innerPadding ->
        Surface (
            modifier = Modifier.fillMaxSize(),
        ){
            NavHost(
                navController = navController,
                startDestination = SearchScreenRoute
            ) {
                composable<SearchScreenRoute> {
                    SearchScreen(
                        modifier = Modifier.padding(innerPadding),
                        onIdClick = {navController.navigate(DetailsScreenRoute(it))}
                    )
                }
                composable<DetailsScreenRoute>{
                    ArtDetailsScreen(
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                    )
                    println(navController.previousBackStackEntry != null)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {Text(
            stringResource(R.string.app_name)
        )},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        }
    )
}