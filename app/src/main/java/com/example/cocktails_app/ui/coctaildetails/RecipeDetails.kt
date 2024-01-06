package com.example.cocktails_app.ui.coctaildetails

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.example.cocktails_app.ui.ingredients.IngredientsAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class RecipeDetails : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cocktailId = intent.getIntExtra("COCKTAIL_ID", 0)
        if (cocktailId != 0) {

            fetchCocktailDetails(cocktailId)
        } else {
            Toast.makeText(this, "Invalid Cocktail ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchCocktailDetails(cocktailId: Int) {
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$cocktailId")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {

                    Toast.makeText(this@RecipeDetails, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()
                    val cocktail = parseCocktailDetails(responseData)
                    runOnUiThread {
                        updateUI(cocktail)

                    }
                }
            }
        })
    }

    private fun parseCocktailDetails(json: String): Cocktail? {
        val gson = Gson()
        val response = gson.fromJson(json, CocktailApiResponse::class.java)
        return response.drinks?.firstOrNull()
    }

    private fun updateUI(cocktail: Cocktail?) {
        cocktail?.let {
            Picasso.get().load(it.cocktailImage).into(binding.imageView2)
            binding.textView.text = it.cocktailName
            binding.instruction.text = it.cocktailInstructions
            binding.category.text = it.category
            binding.glass.text = it.glass

            // Set up ingredients RecyclerView
            val ingredientsAdapter = IngredientsAdapter(it.getFormattedIngredients())
            binding.recyclerViewIngredients.adapter = ingredientsAdapter
        } ?: runOnUiThread {
            Toast.makeText(this, "Cocktail details not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    data class CocktailApiResponse(val drinks: List<Cocktail>?)
}