package com.example.cocktails_app.core.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("drinks") val drinks: List<Drinks>
)

data class Drinks(
    @SerializedName("strIngredient1") val ingredientName: String
)
