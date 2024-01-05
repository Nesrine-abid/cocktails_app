package com.example.cocktails_app.core.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Cocktails(
    @SerializedName("drinks") val drinks: List<Cocktail>
)
data class Cocktail(
    @SerializedName("idDrink") val cocktailId: Int,
    @SerializedName("strDrink") val cocktailName: String,
    @SerializedName("strDrinkThumb") val cocktailImage: String,
    @SerializedName("strInstructions") val cocktailInstructions: String,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strGlass") val glass: String?,

    // Ingredients
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,
    @SerializedName("strIngredient7") val ingredient7: String?,
    @SerializedName("strIngredient8") val ingredient8: String?,
    // ... include up to strIngredient8

    // Measures
    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?,
    @SerializedName("strMeasure7") val measure7: String?,
    @SerializedName("strMeasure8") val measure8: String?,


    // ... include up to strMeasure8

) : Parcelable {

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        //ingredients

        parcel.readString() ,
        parcel.readString() ,
        parcel.readString(),
        parcel.readString() ,
        parcel.readString() ,
        parcel.readString() ,
        parcel.readString() ,
        parcel.readString(),
        //measure
        parcel.readString() ,
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ,
        parcel.readString() ,
        parcel.readString(),
        parcel.readString() ,
        parcel.readString() ,

    )

    // Write object data to the parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cocktailId)
        parcel.writeString(cocktailName)
        parcel.writeString(cocktailImage)
        parcel.writeString(cocktailInstructions)

        parcel.writeString(ingredient1)
        parcel.writeString(ingredient2)
        parcel.writeString(ingredient3)
        parcel.writeString(ingredient4)
        parcel.writeString(ingredient5)
        parcel.writeString(ingredient6)
        parcel.writeString(ingredient7)
        parcel.writeString(ingredient8)

        parcel.writeString(measure1)
        parcel.writeString(measure2)
        parcel.writeString(measure3)
        parcel.writeString(measure4)
        parcel.writeString(measure5)
        parcel.writeString(measure6)
        parcel.writeString(measure7)
        parcel.writeString(measure8)


    }

    // Describe the kinds of special objects contained in this Parcelable instance
    override fun describeContents(): Int {
        return 0
    }

    // Creator for Parcelable
    companion object CREATOR : Parcelable.Creator<Cocktail> {
        // Create a new instance of the Parcelable class, instantiating it from the given parcel
        override fun createFromParcel(parcel: Parcel): Cocktail {
            return Cocktail(parcel)
        }

        // Create a new array of the Parcelable class
        override fun newArray(size: Int): Array<Cocktail?> {
            return arrayOfNulls(size)
        }
    }

    // A function to extract the ingredients and their measures as a list of pairs
    fun getFormattedIngredients(): List<Drinks> {
        val formattedIngredients = mutableListOf<Drinks>()

        // Loop through each ingredient and its measure, and create a Drinks object
        if (!ingredient1.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient1: ${measure1 ?: ""}"))
        if (!ingredient2.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient2: ${measure2 ?: ""}"))
        if (!ingredient3.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient3: ${measure3 ?: ""}"))
        if (!ingredient4.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient4: ${measure4 ?: ""}"))
        if (!ingredient5.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient5: ${measure5 ?: ""}"))
        if (!ingredient6.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient6: ${measure6 ?: ""}"))
        if (!ingredient7.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient7: ${measure7 ?: ""}"))
        if (!ingredient8.isNullOrBlank()) formattedIngredients.add(Drinks("$ingredient8: ${measure8 ?: ""}"))

        return formattedIngredients
    }
}