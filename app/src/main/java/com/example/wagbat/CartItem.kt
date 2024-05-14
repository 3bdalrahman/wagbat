package com.example.wagbat

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class CartItem(
    val dishId: String? = null,
    val dishImage: String? = null,
    val dishName: String? = null,
    val dishPrice: Int = 0,
    var quantity: Int = 0,
    var totalPrice: Int = 0,
    var price: Int = 0, // Added price property
    @get:Exclude @set:Exclude var status: String = "", // Ignored field
    @get:Exclude @set:Exclude var date: String = "",   // Ignored field
    @get:Exclude @set:Exclude var time: String = ""    // Ignored field
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt() // Added reading price from parcel
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(dishId)
        dest.writeString(dishImage)
        dest.writeString(dishName)
        dest.writeInt(dishPrice)
        dest.writeInt(quantity)
        dest.writeInt(totalPrice)
        dest.writeInt(price) // Added writing price to parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(source: Parcel): CartItem {
            return CartItem(source)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
