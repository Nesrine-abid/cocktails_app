package com.example.cocktails_app.ui.ingredients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Drink
import com.example.cocktails_app.core.model.Drinks
import com.example.cocktails_app.core.model.Ingredient
import com.example.cocktails_app.core.service.CategoriesFetcher
import com.example.cocktails_app.core.service.IngredientsFetcher
import com.example.cocktails_app.ui.categories.CategoriesAdapter
import com.example.cocktails_app.ui.categories.SelectedCocktail
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class IngredientsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientsAdapter
    private lateinit var loaderImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2)
        loaderImageView = view.findViewById(R.id.ivLoader)

        recyclerView = view.findViewById(R.id.recyclerViewSearch1)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        showLoader()
        fetchIngredients()
    }

    private fun fetchIngredients() {
        IngredientsFetcher.fetchIngredients() { ingredients ->
            activity?.runOnUiThread {
                adapter = ingredients?.let { IngredientsAdapter(ingredients.drinks) }!!
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                hideLoader()

                adapter.onItemClick = { selectedIngredient: Drinks ->
                    val intent = Intent(context, SelectIngredientAct::class.java)
                    intent.putExtra("INGREDIENT", selectedIngredient.ingredientName)
                    Log.d("SelectedCocktail", "Received ingredientName: ${selectedIngredient.ingredientName}")
                    startActivity(intent)
                }
            }
        }
    }
    private fun showLoader() {
        activity?.runOnUiThread {
            loaderImageView.visibility = View.VISIBLE
            val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            loaderImageView.startAnimation(rotateAnimation)
            Log.d("CategoriesFragment", "Showing loader")
        }
    }

    private fun hideLoader() {
        loaderImageView.postDelayed({
            activity?.runOnUiThread {
                loaderImageView.clearAnimation()
                loaderImageView.visibility = View.GONE
            }
        }, 700) // Delay in milliseconds
        Log.d("CategoriesFragment", "Hiding loader")
    }
}
