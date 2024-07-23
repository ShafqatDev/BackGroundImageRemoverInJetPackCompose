package com.example.backgroundimageremoverinjetpackcompose.myapp

import android.app.Application
import com.example.backgroundimageremoverinjetpackcompose.di.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(moduleList)
        }
    }
}