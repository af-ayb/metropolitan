package com.example.metropolian_museum

import android.app.Application
import com.example.metropolian_museum.data.AppContainer
import com.example.metropolian_museum.data.DefaultAppContainer

class MetropolianApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}