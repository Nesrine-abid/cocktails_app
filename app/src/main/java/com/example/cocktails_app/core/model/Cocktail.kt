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
) : Parcelable {
    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Write object data to the parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cocktailId)
        parcel.writeString(cocktailName)
        parcel.writeString(cocktailImage)
        parcel.writeString(cocktailInstructions)
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
}
