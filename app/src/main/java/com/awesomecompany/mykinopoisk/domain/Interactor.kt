package com.awesomecompany.mykinopoisk.domain

import com.awesomecompany.mykinopoisk.data.*
import com.awesomecompany.mykinopoisk.data.entity.TmdbResultsDto
import com.awesomecompany.mykinopoisk.utils.Converter
import com.awesomecompany.mykinopoisk.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val retrofitService: TmdbApi) {

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(ApiKey.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>
                ) {
                    callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.tmdbFilms))
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    callback.onFailure()
                }
            })
    }
}