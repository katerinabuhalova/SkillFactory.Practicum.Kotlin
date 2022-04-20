package com.awesomecompany.mykinopoisk.di

import com.awesomecompany.mykinopoisk.di.modules.DatabaseModule
import com.awesomecompany.mykinopoisk.di.modules.DomainModule
import com.awesomecompany.mykinopoisk.di.modules.RemoteModule
import dagger.Component
import javax.inject.Singleton
import com.awesomecompany.mykinopoisk.viewmodel.HomeFragmentViewModel as HomeFragmentViewModel1

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel1)
}