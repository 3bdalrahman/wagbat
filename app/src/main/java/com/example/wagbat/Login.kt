package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signinBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        signinBtn = findViewById(R.id.LOGNINBTN)
        email = findViewById(R.id.LoginEmail)
        password = findViewById(R.id.LoginPassword)
        signinBtn.setOnClickListener {
            var emailTxt = email.text.toString()
            var passwordTxt = password.text.toString()
            if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()
                        val user = auth.currentUser
                        var intent = Intent(this, Home::class.java)
                        intent.putExtra("user",user)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Faild", Toast.LENGTH_LONG).show()

                    }
                }

        }

    }


}