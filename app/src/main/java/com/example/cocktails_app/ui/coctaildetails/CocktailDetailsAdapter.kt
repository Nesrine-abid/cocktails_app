package com.example.cocktails_app.ui.coctaildetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails_app.R
import com.example.cocktails_app.core.model.Cocktail
import com.squareup.picasso.Picasso
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView

class CocktailDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image: ShapeableImageView = itemView.findViewById(R.id.imageView2)
    val cocktailName: TextView = itemView.findViewById(R.id.textView)
    val ingredientsTitle: TextView = itemView.findViewById(R.id.ingredientsTitle)
    val instructionsTitle: TextView = itemView.findViewById(R.id.instructionsTitle)
    val instructions: TextView = itemView.findViewById(R.id.instruction)
}

class CocktailDetailsAdapter(private val cocktails: List<Cocktail>) :
    RecyclerView.Adapter<CocktailDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_recipe_details, parent, false)
        return CocktailDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CocktailDetailsViewHolder, position: Int) {
        val cocktail = cocktails[position]
        Picasso.get().load(cocktail.cocktailImage).into(holder.image)
        holder.cocktailName.text = cocktail.cocktailName
        holder.instructions.text = cocktail.cocktailInstructions

        // Assuming getFormattedIngredients() is implemented in the Cocktail class
        holder.ingredientsTitle.text = cocktail.getFormattedIngredients().joinToString("\n")
    }

    override fun getItemCount(): Int = cocktails.size
}
