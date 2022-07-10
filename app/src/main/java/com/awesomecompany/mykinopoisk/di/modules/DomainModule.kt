package com.awesomecompany.mykinopoisk.di.modules

import android.content.Context
import com.awesomecompany.mykinopoisk.data.MainRepository
import com.awesomecompany.mykinopoisk.data.PreferenceProvider
import com.awesomecompany.remote_module.TmdbApi
import com.awesomecompany.mykinopoisk.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(tmdbApi: com.awesomecompany.remote_module.TmdbApi, preferenceProvider: PreferenceProvider, repository: MainRepository) = Interactor(retrofitService = tmdbApi, preferences = preferenceProvider, repo = repository)
}