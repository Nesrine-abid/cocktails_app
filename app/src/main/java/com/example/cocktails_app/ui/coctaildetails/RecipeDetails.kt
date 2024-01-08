package com.example.cocktails_app.ui.coctaildetails

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.service.CocktailDetailsFetcher
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.example.cocktails_app.ui.ingredients.IngredientsAdapter
import com.squareup.picasso.Picasso

class RecipeDetails : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding

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
        CocktailDetailsFetcher.fetchCocktailDetails(cocktailId) { cocktail ->
            runOnUiThread {
                updateUI(cocktail)
            }
        }
    }
    private fun updateUI(cocktail: Cocktail?) {
        cocktail?.let {
            Picasso.get().load(it.cocktailImage).into(binding.imageView2)
            binding.textView.text = it.cocktailName
            binding.instruction.text = it.cocktailInstructions
            binding.category.text = it.category
            binding.glass.text = it.glass

            val ingredientsAdapter = IngredientsAdapter(it.getFormattedIngredients())
            binding.recyclerViewIngredients.adapter = ingredientsAdapter
        } ?: runOnUiThread {
            Toast.makeText(this, "Cocktail details not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}