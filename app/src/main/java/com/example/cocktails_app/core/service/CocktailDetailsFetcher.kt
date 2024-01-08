package com.example.cocktails_app.core.service

import com.example.cocktails_app.core.model.Cocktail
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object CocktailDetailsFetcher {

    private val client = OkHttpClient()

    fun fetchCocktailDetails(cocktailId: Int, callback: (Cocktail?) -> Unit) {
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$cocktailId")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()
                    val cocktail = parseCocktailDetails(responseData)
                    callback(cocktail)
                } ?: callback(null)
            }
        })
    }

    private fun parseCocktailDetails(json: String): Cocktail? {
        val gson = Gson()
        val response = gson.fromJson(json, CocktailApiResponse::class.java)
        return response.drinks?.firstOrNull()
    }

    data class CocktailApiResponse(val drinks: List<Cocktail>?)
}

