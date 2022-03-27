package com.awesomecompany.mykinopoisk.domain

import com.awesomecompany.mykinopoisk.data.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class Interactor(val repo: MainRepository) {
    fun getFilmsLocalDB(): List<Film> = repo.filmsDataBase
    fun getFilmsRemoutDB(): List<Film> {

        val result: ArrayList<Film> = arrayListOf()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/550?api_key=${key}")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                try {
                    val responseBody = response.body()
                    val card = gson.fromJson(responseBody?.string(), FilmCard::class.java)
                    val film = Film(card.title, 0, card.overview, 5f)
                    result.add(film)
                } catch (e: Exception) {
                    println(response)
                    e.printStackTrace()
                }
            }
        })
        return result
    }

    companion object {
        const val key = "41fed2aa069a1cd0703d15824da3314"
    }
}