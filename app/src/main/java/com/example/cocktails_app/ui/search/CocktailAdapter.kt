package com.example.cocktails_app.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.google.android.material.imageview.ShapeableImageView

class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
    val heading : TextView = itemView.findViewById (R.id.tvHeading)
}

class CocktailAdapter(private val cocktails: ArrayList<Cocktail>) : RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        )
        return DataViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = cocktails[position]
        currentItem.titleImage?.let { holder.titleImage.setImageResource(it) }
        holder.heading.text = currentItem.heading
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }


}


