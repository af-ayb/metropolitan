package com.example.metropolian_museum

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.metropolian_museum.ui.MetropolianMuseumApp
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
                    MetropolianMuseumApp()

            }
        }
    }
}
