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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var signinBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.reference.child("users")
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
            database.orderByChild("email").equalTo(emailTxt).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(userSnapshot in dataSnapshot.children){
                            val userData = userSnapshot.getValue(UserData::class.java)
                            if (userData != null && userData.password == passwordTxt){
                                Toast.makeText(this@Login,"Login Sucessful",Toast.LENGTH_SHORT).show()
                                var intent = Intent(this@Login, Home::class.java)
                                intent.putExtra("user",userData.email)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    Toast.makeText(this@Login,"Login Failed",Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Login, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }


}