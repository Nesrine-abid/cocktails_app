package com.example.cocktails_app.ui.search
import FavoriteFragment
import android.content.Context
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
import com.example.cocktails_app.core.service.CocktailsByCategoryFetcher
import com.example.cocktails_app.core.service.SearchByNameFetcher
import com.example.cocktails_app.ui.coctaildetails.RecipeDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val SharedPreferencesKey = "mySharedPreferences"
private const val SavedValueKey = "savedCheckedItems"
private const val FAVORITE_FRAGMENT_TAG = "FavoriteFragment"
class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var RandomCocktails: List<Cocktail> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadCheckedItems()

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

        val categories = listOf("Ordinary Drink", "Cocktail", "Shake", "Other / Unknown", "Cocoa", "Shot", "Coffee / Tea", "Homemade Liqueur", "Punch / Party Drink", "Beer", "Soft Drink")
        val randomCategory = categories[Random.nextInt(categories.size)]
        fetchCocktailsByCategory(randomCategory)

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

    private fun saveCheckedItem(cocktailId: Int, isChecked: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE)
        val checkedItemsJson = sharedPreferences.getString(SavedValueKey, null)

        checkedItemsJson?.let {
            val checkedItems = Gson().fromJson<MutableList<Pair<Int, Boolean>>>(it, object : TypeToken<List<Pair<Int, Boolean>>>() {}.type)
                .toMutableList()

            val index = checkedItems.indexOfFirst { it.first == cocktailId }
            if (index != -1) {
                checkedItems[index] = Pair(cocktailId, isChecked)
            } else {
                checkedItems.add(Pair(cocktailId, isChecked))
            }

            sharedPreferences.edit().apply {
                putString(SavedValueKey, Gson().toJson(checkedItems))
                apply()
            }
        }
    }
    private fun loadCheckedItems(): List<Pair<Int, Boolean>> {
        val sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE)
        val checkedItemsJson = sharedPreferences.getString(SavedValueKey, null)

        return checkedItemsJson?.let {
            Gson().fromJson<List<Pair<Int, Boolean>>>(it, object : TypeToken<List<Pair<Int, Boolean>>>() {}.type)
        } ?: emptyList()
    }

    private fun fetchCocktailsByCategory(categoryName: String?) {
        CocktailsByCategoryFetcher.fetchCocktailsByCategory(categoryName) { cocktails ->
            activity?.runOnUiThread {
                val adapter = cocktails?.let { CocktailAdapter(cocktails.drinks) }
                if (cocktails != null) {
                    RandomCocktails = cocktails.drinks
                }

                recyclerView.adapter = adapter
                adapter?.notifyDataSetChanged()

                adapter?.onItemClick = { selectedCocktail: Cocktail ->
                    val intent = Intent(context, RecipeDetails::class.java)
                    intent.putExtra("COCKTAIL_ID", selectedCocktail.cocktailId)
                    startActivity(intent)
                }

                adapter?.onItemCheckChanged = { isChecked, position ->
                    val selectedCocktail = RandomCocktails[position]

                    if (isChecked) {
                        showToast("Item added to Favorites")
                        saveCheckedItem(selectedCocktail.cocktailId, true)

                    } else {
                        showToast("Item removed from Favorites")
                        saveCheckedItem(selectedCocktail.cocktailId, false)
                    }

                    // Notify the FavoriteFragment about the change
                    val favoriteFragment = fragmentManager?.findFragmentByTag(FAVORITE_FRAGMENT_TAG) as? FavoriteFragment
                    favoriteFragment?.updateFavoriteList()
                }
            }
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }
    private fun filterList(query: String?) {
        if (!query.isNullOrBlank()) {
            fetchCocktailsByQuery(query)
        } else {
            val adapter = recyclerView.adapter as? CocktailAdapter
            adapter?.setFilteredList(RandomCocktails)
        }
    }
    private fun fetchCocktailsByQuery(query: String) {
        val filteredList = ArrayList<Cocktail>()

        SearchByNameFetcher.fetchCocktailsByName(query) { cocktails ->
            cocktails?.let {
                filteredList.addAll(it.drinks ?: emptyList())

                activity?.runOnUiThread {
                    val adapter = recyclerView.adapter as? CocktailAdapter
                    adapter?.setFilteredList(filteredList)
                }
            }
        }
    }
}

