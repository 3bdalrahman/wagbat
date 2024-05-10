package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUp : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var signup_button: Button
    private lateinit var fullname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login_txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        enableEdgeToEdge()

        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.reference.child("users")

        signup_button = findViewById(R.id.SINGUPBTN)
        fullname = findViewById(R.id.Fullname)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
        login_txt = findViewById(R.id.LTXT)

        login_txt.setOnClickListener {
            val nextpage = Intent(this, Login::class.java)
            startActivity(nextpage)
        }

        signup_button.setOnClickListener {
            Log.d("SignUp", "Signup button clicked")
            val fullname_txt = fullname.text.toString()
            val email_txt = email.text.toString()
            val password_txt = password.text.toString()

            if (fullname_txt.isEmpty() || email_txt.isEmpty() || password_txt.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SignUp", "Attempting database query")
            database.orderByChild("email").equalTo(email_txt).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (!datasnapshot.exists()) {
                        val id = database.push().key
                        val userData = UserData(id, fullname_txt, email_txt, password_txt)
                        database.child(id!!).setValue(userData)
                        Toast.makeText(this@SignUp, "User has been Created", Toast.LENGTH_LONG).show()
                        val login_page = Intent(this@SignUp, Login::class.java)
                        startActivity(login_page)
                        finish()
                    } else {
                        Toast.makeText(this@SignUp, "Failed to signup", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@SignUp, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
