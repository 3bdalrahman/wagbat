package com.example.wagbat

import android.os.Parcel
import android.os.Parcelable

data class historyCartDetailsData(
    val dishImage: String? = null,
    val dishName: String? = null,
    val price: Int = 0,
    val quantity: Int = 0,
    val totalPrice: Int = 0,
): Parcelable {constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readInt(),
    parcel.readInt(),
    parcel.readInt()
)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(dishImage)
        dest.writeString(dishName)
        dest.writeInt(price)
        dest.writeInt(quantity)
        dest.writeInt(totalPrice)
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
