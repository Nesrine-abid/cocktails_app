package com.example.cocktails_app.ui.search
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

class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.image)
    val cocktailName : TextView = itemView.findViewById (R.id.cocktailName)
    val checkBox: CheckBox = itemView.findViewById (R.id.cbHeart)

}

class CocktailAdapter(private var cocktails: List<Cocktail>) :
    RecyclerView.Adapter<DataViewHolder>() {

    var onItemCheckChanged: ((Boolean, Int) -> Unit)? = null
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

        // Set the initial checked status for the CheckBox
        holder.checkBox.isChecked = isFavorite(position)

        // Set an OnCheckedChangeListener for the CheckBox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onItemCheckChanged?.invoke(isChecked, position)
        }
    }
    override fun getItemCount(): Int {
        return cocktails.size
    }
    fun setFilteredList(filteredList: List<Cocktail>) {
        this.cocktails = filteredList
        notifyDataSetChanged()
    }
    private fun isFavorite(position: Int): Boolean {
        // Implement logic to check if the cocktail at the given position is in the favorites list
        // Return true if it is a favorite, false otherwise
        return false
    }
}


