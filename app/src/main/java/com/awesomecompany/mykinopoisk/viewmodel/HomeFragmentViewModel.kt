package com.awesomecompany.mykinopoisk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesomecompany.mykinopoisk.App
import com.awesomecompany.mykinopoisk.domain.Film
import com.awesomecompany.mykinopoisk.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData = MutableLiveData<List<Film>>()
    private var interactor: Interactor = App.instance.interactor

    init {

        val films = interactor.getFilmsLocalDB()
        filmsListLiveData.postValue(films)
    }
}