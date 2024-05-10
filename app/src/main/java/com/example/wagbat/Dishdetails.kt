package com.example.wagbat

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dishdetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dishdetails)

        val details= intent.getParcelableExtra<dishs>("name")
        if(details!=null)
        {
            val name : TextView = findViewById(R.id.Home_Screen3_Name)
            val price : TextView = findViewById(R.id.Home_Screen3_Price)
            val img : ImageView = findViewById(R.id.Home_Screen3_Image)

            name.text = details.dish_name
            price.text = details.price
            img.setImageResource(details.dish_img)
        }

    }
}