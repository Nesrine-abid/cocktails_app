package com.example.cocktails_app.ui.coctaildetails

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.service.CocktailDetailsFetcher
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.example.cocktails_app.ui.ingredients.IngredientsAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetails : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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
        coroutineScope.launch {
            try {
                val cocktail = withContext(Dispatchers.IO) {
                    CocktailDetailsFetcher.fetchCocktailDetails(cocktailId)
                }
                updateUI(cocktail)
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@RecipeDetails, "Error fetching cocktail details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
private fun updateUI(cocktail: Cocktail?) {
    coroutineScope.launch {
        withContext(Dispatchers.Main) {
            cocktail?.let {
                Picasso.get().load(it.cocktailImage).into(binding.imageView2)
                binding.textView.text = it.cocktailName
                binding.instruction.text = it.cocktailInstructions
                binding.category.text = it.category
                binding.glass.text = it.glass

                val ingredientsAdapter = IngredientsAdapter(it.getFormattedIngredients())
                binding.recyclerViewIngredients.adapter = ingredientsAdapter
            } ?: run {
                Toast.makeText(this@RecipeDetails, "Cocktail details not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

}