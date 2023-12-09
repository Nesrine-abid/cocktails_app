package com.example.cocktails_app.ui.coctaildetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.example.cocktails_app.ui.search.SearchFragment

class RecipeDetails : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Retrieve the Cocktail object from the intent
        //val cocktail = intent.getSerializableExtra("recipe") as? Cocktail
        val cocktail = intent.getParcelableExtra<Cocktail>("recipe")

//        val cocktail = Cocktail("Margarita", R.drawable.margarita)
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