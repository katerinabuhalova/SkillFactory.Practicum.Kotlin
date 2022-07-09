package com.awesomecompany.mykinopoisk.domain

import com.awesomecompany.mykinopoisk.data.ApiKey
import com.awesomecompany.mykinopoisk.data.MainRepository
import com.awesomecompany.mykinopoisk.data.PreferenceProvider
import com.awesomecompany.mykinopoisk.data.TmdbApi
import com.awesomecompany.mykinopoisk.data.entity.Film
import com.awesomecompany.mykinopoisk.data.entity.TmdbResultsDto
import com.awesomecompany.mykinopoisk.utils.Converter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
    private val repo: MainRepository,
) {

    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        progressBarState.onNext(true)
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), ApiKey.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>
                ) {
                    val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                    Completable.fromSingle<List<Film>> {
                        repo.putToDb(list)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(true)
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    progressBarState.onNext(true)
                }
            })
    }

    fun getSearchResultFromApi(search: String): Observable<List<Film>> = retrofitService.getFilmFromSearch(ApiKey.KEY, "ru-RU", search, 1)
        .map {
            Converter.convertApiListToDtoList(it.tmdbFilms)
        }

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()


}