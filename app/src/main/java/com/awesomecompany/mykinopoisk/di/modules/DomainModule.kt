package com.awesomecompany.mykinopoisk.di.modules

import android.content.Context
import com.awesomecompany.mykinopoisk.data.PreferenceProvider
import com.awesomecompany.mykinopoisk.data.TmdbApi
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
    fun provideInteractor(tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) =
        Interactor(retrofitService = tmdbApi, preferences = preferenceProvider)
}