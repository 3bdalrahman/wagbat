package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Welcom : AppCompatActivity() {
    lateinit var facebookBtn: Button
    lateinit var googleBtn: Button
    lateinit var signInTxt: TextView
    lateinit var signUpBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcom)
        val home = Intent(this, Home::class.java)
        val signup = Intent(this, SignUp::class.java)
        val loginIntent = Intent(this, Login::class.java)

        facebookBtn = findViewById(R.id.FacebookBtn)
        googleBtn = findViewById(R.id.GoogleBtn)
        signInTxt = findViewById(R.id.SinginTexT)
        signUpBtn = findViewById(R.id.SingBtn)

        facebookBtn.setOnClickListener {
            Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()
            startActivity(home)
        }

        googleBtn.setOnClickListener {
            Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()
            startActivity(home)
        }

        signInTxt.setOnClickListener {
            startActivity(loginIntent)
        }

        signUpBtn.setOnClickListener {
            startActivity(signup)
        }
        }
    }
