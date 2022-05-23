package com.awesomecompany.mykinopoisk

import android.app.Application
import com.awesomecompany.mykinopoisk.di.AppComponent
import com.awesomecompany.mykinopoisk.di.modules.DatabaseModule
import com.awesomecompany.mykinopoisk.di.modules.DomainModule
import com.awesomecompany.mykinopoisk.di.modules.RemoteModule
import com.awesomecompany.mykinopoisk.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}