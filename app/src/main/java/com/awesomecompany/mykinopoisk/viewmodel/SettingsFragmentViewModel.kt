package com.awesomecompany.mykinopoisk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesomecompany.mykinopoisk.App
import com.awesomecompany.mykinopoisk.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor
    val categoryPropertyLifeData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        getCategoryProperty()
    }

    private fun getCategoryProperty() {
        categoryPropertyLifeData.value = interactor.getDefaultCategoryFromPreferences()
    }

    fun putCategoryProperty(category: String) {
        interactor.saveDefaultCategoryToPreferences(category)
        getCategoryProperty()
    }
}