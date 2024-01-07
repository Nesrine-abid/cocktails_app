import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktails
import com.example.cocktails_app.ui.search.CocktailAdapter
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SelectedCocktail1 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_cocktail)

        recyclerView = findViewById(R.id.recyclerViewSearch3) // Initialize recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Retrieve the "INGREDIENT" extra correctly
        val ingredientName = intent.getStringExtra("INGREDIENT")
        supportActionBar?.title = ingredientName

        if (ingredientName != null) {
            fetchCocktailsByCategory(ingredientName)
        } else {
            // Handle the case when ingredientName is null
            // You can display an error message or handle it as needed
            Log.d("SelectedCocktail", "ingredientName is null")
        }
    }

    private fun fetchCocktailsByCategory(ingredientName: String?) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=$ingredientName")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle network request failure
                Log.e("SelectedCocktail", "Network request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    // Handle non-successful response (e.g., HTTP error codes)
                    Log.e("SelectedCocktail", "HTTP error code: ${response.code}")
                    return
                }

                response.body?.let {
                    try {
                        val responseData = it.string()
                        Log.d("SelectedCocktail", "JSON Response: $responseData") // Log the JSON response

                        val gson = Gson()
                        val selectedCocktails = gson.fromJson(responseData, Cocktails::class.java)

                        runOnUiThread {
                            if (selectedCocktails != null && selectedCocktails.drinks != null) {
                                val adapter = CocktailAdapter(selectedCocktails.drinks)
                                recyclerView.adapter = adapter
                                adapter.notifyDataSetChanged()
                            } else {
                                // Handle the case when selectedCocktails or its drinks are null
                                Log.e("SelectedCocktail", "SelectedCocktails or its drinks are null")
                            }
                        }
                    } catch (e: Exception) {
                        // Handle JSON parsing error
                        Log.e("SelectedCocktail", "JSON parsing error: ${e.message}")
                    }
                }
            }
        })
    }
}


