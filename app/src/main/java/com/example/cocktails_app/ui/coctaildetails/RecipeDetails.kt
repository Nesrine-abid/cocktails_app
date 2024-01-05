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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the Cocktail object from the intent
        val cocktail = intent.getParcelableExtra<Cocktail>("recipe")

        if (cocktail != null) {
            Picasso.get().load(cocktail.cocktailImage).into(binding.imageView2)
            binding.textView.text = cocktail.cocktailName
            binding.instruction.text = cocktail.cocktailInstructions
            binding.category.text = cocktail.category
        } else {
            Toast.makeText(this, "Invalid Cocktail", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}