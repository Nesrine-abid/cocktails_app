package com.example.cocktails_app.core.service

import com.example.cocktails_app.core.model.Category
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object CategoriesFetcher {

    private val client = OkHttpClient()

    fun fetchCategories(callback: (Category?) -> Unit) {
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()

                    val cocktail = parseCategoryDetails(responseData)
                    callback(cocktail)
                } ?: callback(null)
            }
        })
    }

    private fun parseCategoryDetails(json: String): Category? {
        val gson = Gson()
        return gson.fromJson(json, Category::class.java)
    }
}
