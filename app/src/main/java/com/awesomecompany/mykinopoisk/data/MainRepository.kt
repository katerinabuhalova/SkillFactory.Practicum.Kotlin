package com.awesomecompany.mykinopoisk.data

import androidx.lifecycle.LiveData
import com.awesomecompany.mykinopoisk.data.DAO.FilmDao
import com.awesomecompany.mykinopoisk.data.entity.Film
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {

        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()
}