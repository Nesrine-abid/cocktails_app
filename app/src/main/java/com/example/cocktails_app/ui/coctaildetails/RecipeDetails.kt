package com.example.cocktails_app.ui.coctaildetails

import Cocktail
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding

class RecipeDetails : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Retrieve the Cocktail object from the intent
        val cocktail = intent.getParcelableExtra<Cocktail>("recipe")

        if (cocktail != null) {
            binding.imageView2.setImageResource(cocktail.cocktailImage)
            binding.textView.text = cocktail.cocktailName
        } else {
            // Handle the case where no valid Cocktail object is received
            Toast.makeText(this, "Invalid Cocktail", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}