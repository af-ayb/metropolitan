package com.example.metropolian_museum

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.metropolian_museum.network.Art
import com.example.metropolian_museum.ui.MetropolianMuseumApp
import com.example.metropolian_museum.ui.screens.DetailsLayout
import com.example.metropolian_museum.ui.theme.MetropolianMuseumTheme
import dagger.hilt.android.AndroidEntryPoint

// inject in android framework
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetropolianMuseumTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                ){
                    MetropolianMuseumApp()
                }
            }
        }
    }
}
