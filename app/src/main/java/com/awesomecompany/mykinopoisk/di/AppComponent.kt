package com.awesomecompany.mykinopoisk.di

import com.awesomecompany.mykinopoisk.di.modules.DatabaseModule
import com.awesomecompany.mykinopoisk.di.modules.DomainModule
import com.awesomecompany.remote_module.RemoteProvider
import com.awesomecompany.mykinopoisk.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton
import com.awesomecompany.mykinopoisk.viewmodel.HomeFragmentViewModel as HomeFragmentViewModel1

@Singleton
@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel1)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}