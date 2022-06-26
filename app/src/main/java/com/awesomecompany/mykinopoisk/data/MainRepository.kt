package com.awesomecompany.mykinopoisk.data

import com.awesomecompany.mykinopoisk.data.DAO.FilmDao
import com.awesomecompany.mykinopoisk.data.entity.Film
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {

        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()
}