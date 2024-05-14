package com.example.wagbat

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data  class dishs(var dish_img:Int,var dish_name:String, var price:Int, var id:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dish_img)
        parcel.writeString(dish_name)
        parcel.writeInt(price)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<dishs> {
        override fun createFromParcel(parcel: Parcel): dishs {
            return dishs(parcel)
        }

        override fun newArray(size: Int): Array<dishs?> {
            return arrayOfNulls(size)
        }
    }

}