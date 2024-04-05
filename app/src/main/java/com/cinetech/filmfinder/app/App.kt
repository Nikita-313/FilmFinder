package com.cinetech.filmfinder.app

import android.app.Application
import com.cinetech.filmfinder.di.AppComponent
import com.cinetech.filmfinder.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}