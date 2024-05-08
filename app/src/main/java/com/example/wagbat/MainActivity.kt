package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var move=findViewById<Button>(R.id.move)

        move.setOnClickListener {

            var nextpage= Intent(this,SignUp::class.java)
            startActivity(nextpage)
            finish()
        }
        }

}
