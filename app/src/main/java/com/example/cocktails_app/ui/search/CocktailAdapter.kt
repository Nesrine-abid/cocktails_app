package com.example.cocktails_app.ui.search

import Cocktail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.image)
    val cocktailName : TextView = itemView.findViewById (R.id.cocktailName)
}

class CocktailAdapter(private var cocktails: List<Cocktail>) :
    RecyclerView.Adapter<DataViewHolder>() {

    var onItemClick : ((Cocktail) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        )
        return DataViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = cocktails[position]
        Picasso.get().load(currentItem.cocktailImage).into(holder.image)
        holder.cocktailName.text = currentItem.cocktailName

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }
    override fun getItemCount(): Int {
        return cocktails.size
    }
    fun setFilteredList(filteredList: List<Cocktail>) {
        this.cocktails = filteredList
        notifyDataSetChanged()
    }
    
}


