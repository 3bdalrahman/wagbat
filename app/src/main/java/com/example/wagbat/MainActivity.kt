package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var logo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        logo = findViewById(R.id.imageView)

        logo.alpha = 0f
        Handler().postDelayed({

            logo.animate().setDuration(1500).alpha(1f).withEndAction{

                var intent = Intent(this, SignUp::class.java)
                startActivity(intent)
                finish()
            }
        },1000)


    }

}