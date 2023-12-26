package com.example.cocktails_app.core.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("drinks") val drinks: List<Drink>
)

data class Drink(
    @SerializedName("strCategory") val categoryName: String
)
