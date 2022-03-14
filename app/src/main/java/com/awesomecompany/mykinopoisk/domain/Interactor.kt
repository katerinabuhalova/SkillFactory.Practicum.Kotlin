package com.awesomecompany.mykinopoisk.domain

import com.awesomecompany.mykinopoisk.data.*

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}