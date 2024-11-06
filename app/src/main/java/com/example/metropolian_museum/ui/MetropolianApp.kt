package com.example.metropolian_museum.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.metropolian_museum.R
import com.example.metropolian_museum.ui.screens.SearchScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metropolian_museum.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MetropolianMuseumApp(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name)
                    )
                }
            )
        }
    ){
        Surface (
            modifier = Modifier.fillMaxSize(),
        ){
            val artsViewModel: ArtsViewModel = viewModel(factory = ArtsViewModel.Factory )
            HomeScreen(
                artsUiState = artsViewModel.artsUiState,
                contentPadding = it,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}