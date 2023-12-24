package com.example.cocktails_app.ui.search

import Cocktail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.databinding.ActivityCoctailBinding
import com.example.cocktails_app.databinding.ActivityRecipeDetailsBinding
import com.google.android.material.imageview.ShapeableImageView

class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.image)
    val cocktailName : TextView = itemView.findViewById (R.id.cocktailName)
}

class CocktailAdapter(private var cocktails: ArrayList<Cocktail>) :
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
        currentItem.cocktailImage?.let { holder.image.setImageResource(it) }
        holder.cocktailName.text = currentItem.cocktailName

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }
    override fun getItemCount(): Int {
        return cocktails.size
    }
    fun setFilteredList(filteredList: ArrayList<Cocktail>) {
        this.cocktails = filteredList
        notifyDataSetChanged()
    }
    
}


