package com.awesomecompany.mykinopoisk

import android.app.Application
import com.awesomecompany.mykinopoisk.data.MainRepository
import com.awesomecompany.mykinopoisk.domain.Interactor

class App : Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = MainRepository()
        interactor = Interactor(repo)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}