package com.awesomecompany.mykinopoisk.di.modules

import android.content.Context
import com.awesomecompany.mykinopoisk.data.DatabaseHelper
import com.awesomecompany.mykinopoisk.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}