package com.example.cocktails_app.core.service

import com.example.cocktails_app.core.model.Cocktails
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object SearchByNameFetcher {

    private val client = OkHttpClient()

    fun fetchCocktailsByName(query: String?, callback: (Cocktails?) -> Unit) {

        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=$query")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()

                    val cocktails = parseCocktailDetails(responseData)
                    callback(cocktails)
                } ?: callback(null)
            }
        })
    }

    private fun parseCocktailDetails(json: String): Cocktails? {
        val gson = Gson()
        return gson.fromJson(json, Cocktails::class.java)
    }

}

