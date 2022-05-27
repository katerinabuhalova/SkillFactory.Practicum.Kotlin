package com.awesomecompany.mykinopoisk.domain

import androidx.lifecycle.LiveData
import com.awesomecompany.mykinopoisk.data.ApiKey
import com.awesomecompany.mykinopoisk.data.MainRepository
import com.awesomecompany.mykinopoisk.data.PreferenceProvider
import com.awesomecompany.mykinopoisk.data.TmdbApi
import com.awesomecompany.mykinopoisk.data.entity.Film
import com.awesomecompany.mykinopoisk.data.entity.TmdbResultsDto
import com.awesomecompany.mykinopoisk.utils.Converter
import com.awesomecompany.mykinopoisk.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
    private val repo: MainRepository
) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), ApiKey.KEY, "ru-RU", page)
            .enqueue(object :
                Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>
                ) {
                    val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                    list.forEach {
                        repo.putToDb(list)
                    }
                    callback.onSuccess()
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    callback.onFailure()
                }
            })
    }

    fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDB()

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()
}