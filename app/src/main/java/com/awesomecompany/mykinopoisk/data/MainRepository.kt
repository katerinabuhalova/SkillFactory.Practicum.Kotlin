package com.awesomecompany.mykinopoisk.data

import com.awesomecompany.mykinopoisk.R
import com.awesomecompany.mykinopoisk.domain.Film

class MainRepository {

    val filmsDataBase = listOf(
        Film("Joker", R.drawable.joker, "Film1",5.8f),
        Film("Titanic", R.drawable.titanic, "Film2",7.9f),
        Film("Christmas Story", R.drawable.christmasstory, "Film3"),
        Film("Car", R.drawable.car, "Film4"),
        Film("Monsters", R.drawable.monstersinc24_500x749, "Film5"),
        Film("Nemo", R.drawable.nemo, "Film6"),
        Film("Ratatouille", R.drawable.ratatouille, "Film7"),
        Film("Star Wars", R.drawable.picter3, "Film8")
    )
}