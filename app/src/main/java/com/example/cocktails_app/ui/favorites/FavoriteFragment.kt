import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.example.cocktails_app.core.service.CocktailDetailsFetcher
import com.example.cocktails_app.ui.favorites.FavoritesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val SharedPreferencesKey = "mySharedPreferences"
private const val SavedValueKey = "savedCheckedItems"

class FavoriteFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFavorite)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadFavoriteCocktails()
    }


    private fun loadFavoriteCocktails() {
        coroutineScope.launch {
            val sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE)
            val checkedItemsJson = sharedPreferences.getString(SavedValueKey, null)

            checkedItemsJson?.let {
                val checkedItems = Gson().fromJson<List<Pair<Int, Boolean>>>(it, object : TypeToken<List<Pair<Int, Boolean>>>() {}.type)

                val favorites = checkedItems
                    .filter { it.second }
                    .map { it.first }

                val loadedCocktails = mutableListOf<Cocktail>()

                val deferredCocktails = favorites.map { favoriteId ->
                    async {
                        CocktailDetailsFetcher.fetchCocktailDetails(favoriteId)
                    }
                }

                loadedCocktails.addAll(deferredCocktails.awaitAll().filterNotNull())

                val adapter = FavoritesAdapter(loadedCocktails, checkedItems)
                recyclerView.adapter = adapter
            }
        }
    }


    fun updateFavoriteList() {
        loadFavoriteCocktails()
    }
}
