package com.example.cocktails_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cocktails_app.R
import com.example.cocktails_app.databinding.ActivityCoctailBinding
import com.example.cocktails_app.ui.categories.CategoriesFragment
import com.example.cocktails_app.ui.ingredients.IngredientsFragment
import com.example.cocktails_app.ui.search.SearchFragment

class CocktailActivity : AppCompatActivity() {

    lateinit var binding : ActivityCoctailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoctailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(SearchFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.categories_button -> replaceFragment(CategoriesFragment())
                R.id.ingredients_button -> replaceFragment(IngredientsFragment())
                R.id.home_button -> replaceFragment(SearchFragment())
                else ->{
                }
            }
            true
        }
    }
    private fun replaceFragment (fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}