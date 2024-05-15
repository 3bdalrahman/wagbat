package com.example.wagbat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast

class Track : AppCompatActivity() {

    private lateinit var orderFoodTimeText: TextView
    private lateinit var prepareFoodTimeText: TextView
    private lateinit var deliverFoodTimeText: TextView
    private lateinit var orderConfirmFoodTimeText: TextView
    private lateinit var orderFoodTimeBullet: ImageView
    private lateinit var prepareFoodTimeBullet: ImageView
    private lateinit var deliverFoodTimeBullet: ImageView
    private lateinit var orderConfirmFoodTimeBullet: ImageView
    private lateinit var submitBtn : Button
    private lateinit var currentUserId: String
    private var finalTotalPrice: Double = 0.0

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track)

        currentUserId = getUserId(this) ?: ""

        orderFoodTimeText = findViewById(R.id.orderFoodTime)
        prepareFoodTimeText = findViewById(R.id.PrepareFoodTime)
        deliverFoodTimeText = findViewById(R.id.DeliverFoodTime)
        orderConfirmFoodTimeText = findViewById(R.id.orderConfirmedFoodTime)

        orderFoodTimeBullet = findViewById(R.id.orderFoodBullet)
        prepareFoodTimeBullet = findViewById(R.id.PrepareFoodBullet)
        deliverFoodTimeBullet = findViewById(R.id.DeliverFoodButtlet)
        orderConfirmFoodTimeBullet = findViewById(R.id.orderConfirmedFoodBullet)

        submitBtn = findViewById(R.id.submitButton)

        val startTime = intent.getLongExtra("startTime", 0)
        val confirmedDeliverTime = intent.getLongExtra("deliverTime", 0)
        finalTotalPrice = intent.getDoubleExtra("finalTotalPrice",0.0)
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedStartTime = sdf.format(startTime)
        val formattedDeliverTime = sdf.format(confirmedDeliverTime)

        orderFoodTimeText.text = formattedStartTime
        orderConfirmFoodTimeText.text = formattedDeliverTime

        // Calculate prepare time (30 seconds after start time)
        val prepareTime = startTime + (30 * 1000) // 30 seconds after start time
        val formattedPrepareTime = sdf.format(prepareTime)
        prepareFoodTimeText.text = formattedPrepareTime

        // Calculate deliver time (1 minute after start time)
        val deliverTimeAfterPrepare = startTime + (60 * 1000) // 1 minute after start time
        val formattedDeliverTimeAfterPrepare = sdf.format(deliverTimeAfterPrepare)
        deliverFoodTimeText.text = formattedDeliverTimeAfterPrepare

        // Set the starting bullet for orderFoodTimeBullet
        orderFoodTimeBullet.setImageResource(R.drawable.fillbullet)

        // Schedule tasks to update the bullets at different times
        scheduleBulletUpdate(startTime, prepareTime, deliverTimeAfterPrepare, confirmedDeliverTime)

        // Listen for changes in the status node of the user's cart
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val currentUserCartRef: DatabaseReference = database.reference.child("users").child(currentUserId).child("cart")
        currentUserCartRef.child("status").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.getValue(String::class.java)
                status?.let { updateBulletsBasedOnStatus(it) }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        submitBtn.setOnClickListener{
            saveCartToHistory()
        }
    }


    private fun scheduleBulletUpdate(
        startTime: Long,
        prepareTime: Long,
        deliverTimeAfterPrepare: Long,
        deliverTime: Long
    ) {
        // Runnable to change bullet to filled bullet for each step
        val runnablePrepareFood = Runnable {
            prepareFoodTimeBullet.setImageResource(R.drawable.fillbullet)
        }

        val runnableDeliverFood = Runnable {
            deliverFoodTimeBullet.setImageResource(R.drawable.fillbullet)
        }

        val runnableOrderConfirmFood = Runnable {
            orderConfirmFoodTimeBullet.setImageResource(R.drawable.fillbullet)
        }

        // Schedule tasks to update bullets at different times
        handler.postDelayed(runnablePrepareFood, prepareTime - startTime)
        handler.postDelayed(runnableDeliverFood, deliverTimeAfterPrepare - startTime)
        handler.postDelayed(runnableOrderConfirmFood, deliverTime - startTime)
    }

    private fun updateBulletsBasedOnStatus(status: String) {
        when (status) {
            "Preparation Completed" -> prepareFoodTimeBullet.setImageResource(R.drawable.fillbullet)
            "Delivery Completed" -> deliverFoodTimeBullet.setImageResource(R.drawable.fillbullet)
            "Order Confirmed" -> orderConfirmFoodTimeBullet.setImageResource(R.drawable.fillbullet)
            // Add more cases for other statuses if needed
        }
    }

    private fun saveCartToHistory(){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val currentUserCartRef: DatabaseReference = database.reference.child("users").child(currentUserId)
        val cartRef = currentUserCartRef.child("cart")
        cartRef.child("status").setValue("Order Confirmed")

        cartRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartDetails = snapshot.value
                saveToHistory(cartDetails)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun saveToHistory(cartDetails: Any?){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val currentUserRef: DatabaseReference = database.reference.child("users").child(currentUserId)
        Log.d("finalTotalPrice",finalTotalPrice.toString())
        val historyKey = currentUserRef.child("history").push().key?:""
        currentUserRef.child("history").child(historyKey).child("details").setValue(cartDetails)
        currentUserRef.child("history").child(historyKey).child("finalTotalPrice").setValue(finalTotalPrice)
        clearCartData(currentUserRef.child("cart"))
    }
    private fun clearCartData(currentUserCartRef: DatabaseReference){
        currentUserCartRef.removeValue()
        Toast.makeText(this@Track,"Order Confirmed",Toast.LENGTH_SHORT).show()
        var intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    fun getUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.wagbat.USER_ID_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", "")
    }
}
