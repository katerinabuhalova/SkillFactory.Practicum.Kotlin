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
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}