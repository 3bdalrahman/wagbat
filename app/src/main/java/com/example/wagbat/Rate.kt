package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Rate : AppCompatActivity() {

    lateinit var backToHome: Button
    lateinit var submit: Button
    lateinit var ratingBar: RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rate)

        backToHome = findViewById(R.id.BackToHomeBtn)
        submit = findViewById(R.id.submitBtn)
        ratingBar = findViewById(R.id.ratingbar)

        backToHome.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }

        submit.setOnClickListener {
            val rating = ratingBar.rating.toString()
            Toast.makeText(this, "$rating Star", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, EndOfApp::class.java))
        }
    }
}