package com.awesomecompany.mykinopoisk.domain

import com.awesomecompany.mykinopoisk.data.ApiKey
import com.awesomecompany.mykinopoisk.data.MainRepository
import com.awesomecompany.mykinopoisk.data.PreferenceProvider
import com.awesomecompany.remote_module.TmdbApi
import com.awesomecompany.mykinopoisk.data.entity.Film
import com.awesomecompany.remote_module.entity.TmdbResultsDto
import com.awesomecompany.mykinopoisk.utils.Converter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class Interactor(
    private val retrofitService: com.awesomecompany.remote_module.TmdbApi,
    private val preferences: PreferenceProvider,
    private val repo: MainRepository,
) {

    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), ApiKey.KEY, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDtoList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
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