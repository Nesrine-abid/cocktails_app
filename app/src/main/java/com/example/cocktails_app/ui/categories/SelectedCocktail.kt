package com.example.cocktails_app.ui.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktails
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.example.cocktails_app.ui.search.CocktailAdapter
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SelectedCocktail : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_cocktail)

        recyclerView = findViewById(R.id.recyclerViewSearch3) // Initialize recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val categoryName = intent.getStringExtra("CATEGORY")
        supportActionBar?.title = categoryName

        fetchCocktailsByCategory(categoryName)
    }

    private fun fetchCocktailsByCategory(categoryName: String?) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=$categoryName")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val responseData = it.string()
                    val gson = Gson()
                    val selectedCocktails = gson.fromJson(responseData, Cocktails::class.java)

                    runOnUiThread {
                        val adapter = CocktailAdapter(selectedCocktails.drinks)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}


