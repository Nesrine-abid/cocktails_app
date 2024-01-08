package com.example.cocktails_app.ui.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.model.Cocktails
import com.example.cocktails_app.core.model.Drink
import com.example.cocktails_app.core.service.CategoriesFetcher
import com.example.cocktails_app.core.service.CocktailsByCategoryFetcher
import com.example.cocktails_app.ui.coctaildetails.RecipeDetails
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

        recyclerView = findViewById(R.id.recyclerViewSearch3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val categoryName = intent.getStringExtra("CATEGORY")
        supportActionBar?.title = categoryName

        if (categoryName != null) {
            fetchCocktailsByCategory(categoryName)
        } else {
            Log.d("SelectedCocktail", "category Name is null")
        }
    }

    private fun fetchCocktailsByCategory(categoryName: String?) {

        CocktailsByCategoryFetcher.fetchCocktailsByCategory(categoryName) { cocktails ->
                    runOnUiThread {
                        val adapter = cocktails?.let { CocktailAdapter(cocktails.drinks) }
                        recyclerView.adapter = adapter
                        adapter?.notifyDataSetChanged()

                        adapter?.onItemClick = { selectedCocktail: Cocktail ->
                            val intent = Intent(this@SelectedCocktail, RecipeDetails::class.java)
                            intent.putExtra("COCKTAIL_ID", selectedCocktail.cocktailId)
                            startActivity(intent)
                        }
                    }
        }
    }
}


