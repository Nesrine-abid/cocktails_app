package com.example.cocktails_app.ui.ingredients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.model.Cocktails
import com.example.cocktails_app.core.service.CocktailsByCategoryFetcher
import com.example.cocktails_app.core.service.CocktailsByIngredientFetcher
import com.example.cocktails_app.ui.coctaildetails.RecipeDetails
import com.example.cocktails_app.ui.search.CocktailAdapter
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class SelectIngredientAct : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_ingredient1)

        recyclerView = findViewById(R.id.recyclerViewSearch3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val ingredientName = intent.getStringExtra("INGREDIENT")
        supportActionBar?.title = ingredientName

        if (ingredientName != null) {
            fetchCocktailsByCategory(ingredientName)
        } else {
            Log.d("SelectedCocktail", "ingredient Name is null")
        }
    }

    private fun fetchCocktailsByCategory(ingredientName: String?) {

        CocktailsByIngredientFetcher.fetchCocktailsByIngredient(ingredientName) { cocktails ->
            runOnUiThread {
                val adapter = cocktails?.let { CocktailAdapter(cocktails.drinks) }
                recyclerView.adapter = adapter
                adapter?.notifyDataSetChanged()

                adapter?.onItemClick = { selectedCocktail: Cocktail ->
                    val intent = Intent(this@SelectIngredientAct, RecipeDetails::class.java)
                    intent.putExtra("COCKTAIL_ID", selectedCocktail.cocktailId)
                    startActivity(intent)
                }
            }
        }
    }
}


