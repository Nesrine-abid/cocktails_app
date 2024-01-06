package com.example.cocktails_app.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Category
import com.example.cocktails_app.core.model.Drink


class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val drinks : TextView = itemView.findViewById(R.id.categoryName)
}
class CategoriesAdapter (private var categories: List<Drink>) :
    RecyclerView.Adapter<DataViewHolder>(){

     var onItemClick: ((Drink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item, parent, false
        )
        return DataViewHolder(itemview)
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = categories[position]
        holder.drinks.text = currentItem.categoryName

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }

    }
    override fun getItemCount(): Int {
        return categories.size
    }
}