package com.example.cocktails_app.ui.categories

import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Category
import com.example.cocktails_app.core.model.Drink
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoriesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriesAdapter
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
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use GridLayoutManager for a grid of items
        val layoutManager = GridLayoutManager(context, 2) // 2 is the span count for 2 columns
        loaderImageView = view.findViewById(R.id.ivLoader)

        recyclerView = view.findViewById(R.id.recyclerViewSearch2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        showLoader()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list")
            .build()

        // Use enqueue for asynchronous network calls
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.let {
                    val responseData = it.string()
                    val gson = Gson()
                    val categories = gson.fromJson(responseData, Category::class.java)

                    activity?.runOnUiThread {
                        adapter = CategoriesAdapter(categories.drinks)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                        hideLoader()

                        adapter.onItemClick = { selectedCategory: Drink ->
                            val intent = Intent(context, SelectedCocktail::class.java)
                            intent.putExtra("CATEGORY", selectedCategory.categoryName)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
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
