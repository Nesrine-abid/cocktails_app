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
import com.example.cocktails_app.ui.coctaildetails.RecipeDetails
import java.util.Locale
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CocktailAdapter
    private lateinit var cocktailsArrayList: ArrayList<Cocktail>
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
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerViewSearch)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CocktailAdapter(cocktailsArrayList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        adapter.onItemClick = { selectedCocktail ->
            val intent = Intent(this.context, RecipeDetails::class.java)
            intent.putExtra("recipe", selectedCocktail)
            startActivity(intent)
        }


    }

    private fun filterList(query: String?) {
        if (!query.isNullOrBlank()) {
            val filteredList = ArrayList<Cocktail>()
            for (i in cocktailsArrayList) {
                if (i.cocktailName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun dataInitialize() {
        cocktailsArrayList = arrayListOf<Cocktail>()
        val imageId = arrayOf(
            R.drawable.margarita,
            R.drawable.mojito,
            R.drawable.background,
            R.drawable.negroni,
            R.drawable.drymartini,
            R.drawable.whiskeysour,
            R.drawable.bourbon,
            R.drawable.oldfashioned
        )
        val cocktailsName = arrayOf(
            "Margarita",
            "Mojito",
            "Red cocktail",
            "Negroni",
            "Dry Martini",
            "Whiskey Sour",
            "Bourbon",
            "Old Fashioned"
        )
        for (i in imageId.indices) {
            val cocktail = Cocktail(cocktailsName[i], imageId[i])
            cocktailsArrayList.add(cocktail)
        }
    }
}

