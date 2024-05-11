package com.example.wagbat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class nav_header : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nav_header)

        val user_name= intent.getStringExtra("user")
        val user_email=intent.getStringExtra("email")

        val user = findViewById<TextView>(R.id.userName)
        val email = findViewById<TextView>(R.id.userEmail)

        user.text = user_name
        email.text = user_email


        }
}