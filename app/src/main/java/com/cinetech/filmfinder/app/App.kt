package com.cinetech.filmfinder.app

import android.app.Application
import android.content.Context
import com.cinetech.filmfinder.di.AppComponent
import com.cinetech.filmfinder.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent:AppComponent
    get() = when (this){
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }