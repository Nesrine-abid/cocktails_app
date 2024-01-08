package com.example.cocktails_app.core.service

import com.example.cocktails_app.core.model.Category
import com.example.cocktails_app.core.model.Ingredient
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object IngredientsFetcher {

    private val client = OkHttpClient()

    fun fetchIngredients(callback: (Ingredient?) -> Unit) {
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()

                    val ingredients = parseIngredientDetails(responseData)
                    callback(ingredients)
                } ?: callback(null)
            }
        })
    }

    private fun parseIngredientDetails(json: String): Ingredient? {
        val gson = Gson()
        return gson.fromJson(json, Ingredient::class.java)
    }
}
