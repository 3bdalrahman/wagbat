package com.example.wagbat

import android.os.Parcel
import android.os.Parcelable

data  class item(var resturant_img:Int,var resturant_nam:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(resturant_img)
        parcel.writeString(resturant_nam)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<item> {
        override fun createFromParcel(parcel: Parcel): item {
            return item(parcel)
        }

        override fun newArray(size: Int): Array<item?> {
            return arrayOfNulls(size)
        }
    }
}