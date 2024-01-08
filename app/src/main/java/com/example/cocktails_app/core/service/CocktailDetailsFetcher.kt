package com.example.cocktails_app.core.service

import com.example.cocktails_app.core.model.Cocktail
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object CocktailDetailsFetcher {

    private val client = OkHttpClient()

    // Suspendable function
    suspend fun fetchCocktailDetails(cocktailId: Int): Cocktail? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$cocktailId")
                .build()

            try {
                val response = client.newCall(request).execute()
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()
                    parseCocktailDetails(responseData)
                }
            } catch (e: Exception) {
                null
            }
        }
    }


    private fun parseCocktailDetails(json: String): Cocktail? {
        val gson = Gson()
        val response = gson.fromJson(json, CocktailApiResponse::class.java)
        return response.drinks?.firstOrNull()
    }

    data class CocktailApiResponse(val drinks: List<Cocktail>?)
}

