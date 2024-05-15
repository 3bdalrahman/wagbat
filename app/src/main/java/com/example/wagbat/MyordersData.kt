package com.example.wagbat

import android.os.Parcel
import android.os.Parcelable

data class MyordersData(
    val cartId: String? = null,
    val date: String? = null,
    val time: String? = null,
    val finalTotalPrice: Double = 0.0
): Parcelable {constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readDouble()
)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(cartId)
        dest.writeString(date)
        dest.writeString(time)
        dest.writeDouble(finalTotalPrice)
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
