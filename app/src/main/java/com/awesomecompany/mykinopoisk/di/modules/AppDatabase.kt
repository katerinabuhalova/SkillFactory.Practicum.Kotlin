package com.awesomecompany.mykinopoisk.di.modules

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awesomecompany.mykinopoisk.data.DAO.FilmDao
import com.awesomecompany.mykinopoisk.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}