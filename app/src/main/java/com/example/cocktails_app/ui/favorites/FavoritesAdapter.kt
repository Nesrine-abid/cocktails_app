package com.example.cocktails_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.image)
    val cocktailName: TextView = itemView.findViewById(R.id.cocktailName)
    val checkBox: CheckBox = itemView.findViewById (R.id.cbHeart)
}

class FavoritesAdapter(private val favorites: List<Cocktail>, private val checkedItems: List<Pair<Int, Boolean>>) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentFavorite = favorites[position]

        Picasso.get().load(currentFavorite.cocktailImage).into(holder.image)
        holder.cocktailName.text = currentFavorite.cocktailName

        // Set the initial checked status for the CheckBox
        val isChecked = checkedItems.any { it.first == currentFavorite.cocktailId && it.second }
        holder.checkBox.isChecked = isChecked
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}

