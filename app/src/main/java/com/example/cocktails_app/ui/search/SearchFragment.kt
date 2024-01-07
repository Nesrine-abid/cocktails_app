package com.example.cocktails_app.ui.search
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.model.Cocktails
import com.example.cocktails_app.core.model.Ingredient
import com.example.cocktails_app.ui.coctaildetails.RecipeDetails
import com.example.cocktails_app.ui.ingredients.IngredientsAdapter
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.Locale
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var originalCocktails: List<Cocktail> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerViewSearch)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Shake")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.let {
                    val responseData = it.string()
                    val gson = Gson()
                    val cocktails = gson.fromJson(responseData, Cocktails::class.java)

                    originalCocktails = cocktails.drinks

                    activity?.runOnUiThread {
                        val adapter = CocktailAdapter(originalCocktails)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()

                        adapter.onItemClick = { selectedCocktail: Cocktail ->
                            val intent = Intent(context, RecipeDetails::class.java)
                            intent.putExtra("COCKTAIL_ID", selectedCocktail.cocktailId)
                            startActivity(intent)
                        }
                    }
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if (!query.isNullOrBlank()) {
            val filteredList = ArrayList<Cocktail>()

            val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="
            val apiUrl = "$baseUrl$query"

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(apiUrl)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    // Handle failure
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    response.body?.let {
                        val responseData = it.string()
                        val gson = Gson()
                        val cocktails = gson.fromJson(responseData, Cocktails::class.java)

                        cocktails.drinks?.let { filteredList.addAll(it) }

                        activity?.runOnUiThread {
                            val adapter = recyclerView.adapter as? CocktailAdapter
                            adapter?.setFilteredList(filteredList)
                        }
                    }
                }
            })
        } else {
            val adapter = recyclerView.adapter as? CocktailAdapter
            adapter?.setFilteredList(originalCocktails)
        }
    }

}

