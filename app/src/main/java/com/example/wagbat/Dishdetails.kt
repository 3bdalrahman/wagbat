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
import android.content.Context
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
class Dishdetails : AppCompatActivity() {

    private lateinit var incBtn: Button
    private lateinit var decBtn: Button
    private lateinit var price: TextView
    private lateinit var counter: TextView
    private lateinit var addToCartBtn: Button
    private lateinit var checkOutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dishdetails)
        val details= intent.getParcelableExtra<dishs>("name")
        val currentUserId = getUserId(this)
        checkOutBtn = findViewById(R.id.gotoCheckout)
        incBtn = findViewById(R.id.inc2Button)
        decBtn = findViewById(R.id.dec2Button)
        price = findViewById(R.id.Home_Screen3_Price)
        counter = findViewById(R.id.inc_dec2)
        addToCartBtn = findViewById(R.id.addtocart2)
        if(details!=null)
        {
            val name : TextView = findViewById(R.id.Home_Screen3_Name)
            val price : TextView = findViewById(R.id.Home_Screen3_Price)
            val img : ImageView = findViewById(R.id.Home_Screen3_Image)
            name.text = details.dish_name
            price.text = details.price.toString()
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
        addToCartBtn.setOnClickListener{
            addToCart(currentUserId.toString(), details?.id?:"", details?.dish_name?:"", details?.price?:0,details?.dish_img?:0,counterInt,priceInt)
        }
        checkOutBtn.setOnClickListener{
            var intent = Intent(this, Cart::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun getUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.wagbat.USER_ID_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", "")
    }

    fun addToCart(userId: String,dishId: String,dishName: String,price:Int,dishImage: Int,quantity:Int,totalPrice:Int){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val currentUserRef: DatabaseReference = database.reference.child("users").child(userId)
        val cartItemId = currentUserRef.child("cart").push().key ?:""

        val cartItemData = HashMap<String,Any>()
        cartItemData["dishId"] = dishId
        cartItemData["dishName"] = dishName
        cartItemData["price"] = price
        cartItemData["dishImage"] = dishImage.toString() // Convert image resource ID to String
        cartItemData["quantity"] = quantity
        cartItemData["totalPrice"] = totalPrice
        currentUserRef.child("cart").child(cartItemId).setValue(cartItemData)
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()
    }
}