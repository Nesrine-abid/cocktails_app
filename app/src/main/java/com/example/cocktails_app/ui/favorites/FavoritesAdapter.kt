package com.example.cocktails_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.image)
    val cocktailName: TextView = itemView.findViewById(R.id.cocktailName)
}

class FavoritesAdapter(private val favorites: List<Cocktail>) :
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
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}
