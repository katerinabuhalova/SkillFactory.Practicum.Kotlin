package com.awesomecompany.mykinopoisk.di.modules

import com.awesomecompany.mykinopoisk.data.TmdbApi
import com.awesomecompany.mykinopoisk.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(tmdbApi: TmdbApi) = Interactor(retrofitService = tmdbApi)
}