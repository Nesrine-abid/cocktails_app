package com.example.cocktails_app.ui.coctaildetails

import Cocktail
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
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
            Picasso.get().load(cocktail.cocktailImage).into(binding.imageView2)
            binding.textView.text = cocktail.cocktailName
            binding.instruction.text = cocktail.cocktailInstructions
        } else {
            // Handle the case where no valid Cocktail object is received
            Toast.makeText(this, "Invalid Cocktail", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}