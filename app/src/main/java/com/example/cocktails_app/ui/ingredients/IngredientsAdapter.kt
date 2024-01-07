package com.example.cocktails_app.ui.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Drink
import com.example.cocktails_app.core.model.Drinks


class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val drinks : TextView = itemView.findViewById(R.id.categoryName)
}
class IngredientsAdapter(private var ingredients: List<Drinks>) :
    RecyclerView.Adapter<IngredientsAdapter.DataViewHolder>() {

    var onItemClick: ((Drinks) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item, parent, false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = ingredients[position]
        holder.drinks.text = currentItem.ingredientName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinks: TextView = itemView.findViewById(R.id.categoryName)
    }
}