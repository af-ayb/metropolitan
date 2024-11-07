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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.metropolian_museum.ui.screens.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    ){  it ->
        Surface (
            modifier = Modifier.fillMaxSize(),
        ){
            val artsViewModel = hiltViewModel<ArtsViewModel>()
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                contentPaddingValues = it
            )
        }
    }
}