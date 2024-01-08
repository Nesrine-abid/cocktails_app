package com.example.cocktails_app.core.service

import android.util.Log
import com.example.cocktails_app.core.model.Cocktails
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object CocktailsByIngredientFetcher {

    private val client = OkHttpClient()

    fun fetchCocktailsByIngredient(ingredientName: String?, callback: (Cocktails?) -> Unit) {
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=$ingredientName")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("SelectedCocktail", "Network request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    // Handle non-successful response (e.g., HTTP error codes)
                    Log.e("SelectedCocktail", "HTTP error code: ${response.code}")
                    return
                }
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

