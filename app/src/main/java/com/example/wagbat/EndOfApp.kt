package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EndOfApp : AppCompatActivity() {


    lateinit var backToHome: Button
    lateinit var exit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end_of_app)

        val backIntent = Intent(this, Home::class.java)

        backToHome = findViewById(R.id.BackToHomeBtn2)
        exit = findViewById(R.id.ExitBtn)

        backToHome.setOnClickListener {
            startActivity(backIntent)
        }
        exit.setOnClickListener {
            finish()
        }
    }
}