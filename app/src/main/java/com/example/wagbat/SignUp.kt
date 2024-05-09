package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signup_button :Button
    private lateinit var fullname :EditText
    private lateinit var email :EditText
    private lateinit var password :EditText
    private lateinit var login_txt :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_sign_up)
        enableEdgeToEdge()

        auth = Firebase.auth
        signup_button = findViewById(R.id.SINGUPBTN)
        fullname = findViewById(R.id.Fullname)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
        login_txt = findViewById(R.id.LTXT)

        signup_button.setOnClickListener {
            var fullname_txt = fullname.text.toString()
            var email_txt = email.text.toString()
            var password_txt = password.text.toString()

            if (fullname_txt.isEmpty() || email_txt.isEmpty() || password_txt.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email_txt, password_txt)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"User has been Created",Toast.LENGTH_LONG).show()
                        var login_page= Intent(this,Login::class.java)
                        startActivity(login_page)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.

                        val errorMessage = task.exception?.message
                        Toast.makeText(this, "Failed: $errorMessage", Toast.LENGTH_LONG).show()

                    }
                }

        }
        login_txt.setOnClickListener {
            var nextpage = Intent (this,Login::class.java)
            startActivity(nextpage)
        }
    }
}