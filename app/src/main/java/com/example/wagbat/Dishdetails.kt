package com.example.wagbat

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class Dishdetails : AppCompatActivity() {

    private lateinit var incBtn: Button
    private lateinit var decBtn: Button
    private lateinit var price: TextView
    private lateinit var counter: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dishdetails)
        val details= intent.getParcelableExtra<dishs>("name")
        incBtn = findViewById(R.id.inc2Button)
        decBtn = findViewById(R.id.dec2Button)
        price = findViewById(R.id.Home_Screen3_Price)
        counter = findViewById(R.id.inc_dec2)

        if(details!=null)
        {
            val name : TextView = findViewById(R.id.Home_Screen3_Name)
            val price : TextView = findViewById(R.id.Home_Screen3_Price)
            val img : ImageView = findViewById(R.id.Home_Screen3_Image)

            name.text = details.dish_name
            price.text = details.price
            img.setImageResource(details.dish_img)
        }
        var priceInt = price.text.toString().toInt()
        var counterInt = 1
        var FirstPrice = price.text.toString().toInt()
        incBtn.setOnClickListener {
            if (counterInt > 0) {  // Check before incrementing
                counterInt++
                priceInt += FirstPrice
            }
            counter.text = counterInt.toString()
            price.text = priceInt.toString()
        }

        decBtn.setOnClickListener {
            if (counterInt > 1) {  // Check before incrementing
                counterInt--
                priceInt -= FirstPrice
            }
            counter.text = counterInt.toString()
            price.text = priceInt.toString()
        }

    }
}