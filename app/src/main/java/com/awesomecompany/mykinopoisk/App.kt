package com.awesomecompany.mykinopoisk

import android.app.Application
import com.awesomecompany.mykinopoisk.data.ApiConstants
import com.awesomecompany.mykinopoisk.data.TmdbApi
import com.awesomecompany.mykinopoisk.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val retrofitService = retrofit.create(TmdbApi::class.java)
        interactor = Interactor(retrofitService)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}