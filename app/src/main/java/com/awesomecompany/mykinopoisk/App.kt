package com.awesomecompany.mykinopoisk

import android.app.Application
import com.awesomecompany.mykinopoisk.di.AppComponent
import com.awesomecompany.mykinopoisk.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.create()

    }

    companion object {
        lateinit var instance: App
            private set
    }
}