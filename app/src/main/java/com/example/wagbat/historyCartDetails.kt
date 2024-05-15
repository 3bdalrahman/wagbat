package com.example.wagbat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Locale

class historyCartDetails : AppCompatActivity() {

    private lateinit var currentUserRef: DatabaseReference
    private lateinit var subtotal: TextView
    private lateinit var total: TextView
    private lateinit var date: TextView
    private lateinit var time: TextView
    private lateinit var detailsRecyclerView : RecyclerView
    private lateinit var dishesList: ArrayList<historyCartDetailsData>
    private lateinit var userId: String
    private  var historyId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_cart_details)
        userId = getUserId(this).toString()
        historyId = intent.getStringExtra("historyCartId") ?: ""
        Log.d("historyCartId",historyId)
        subtotal = findViewById(R.id.subTotal)
        total = findViewById(R.id.finalTotalPrice)
        date = findViewById(R.id.date)
        time = findViewById(R.id.time)
        detailsRecyclerView = findViewById(R.id.cart_details)
        detailsRecyclerView.layoutManager = LinearLayoutManager(this)
        detailsRecyclerView.setHasFixedSize(true)
        dishesList = arrayListOf<historyCartDetailsData>()
        getDishesList()
        setValuesToViews()
    }

    private fun getDishesList(){
        Log.d("historyCartId",historyId)
        val currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("history").child(historyId).child("details")
        currentUserRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dishesList.clear()
                if(snapshot.exists()){
                    for(dishSnap in snapshot.children){
                        if(dishSnap.key !="dishId"){
                            val dishImage = dishSnap.child("dishImage").getValue(String::class.java)
                            val dishName = dishSnap.child("dishName").getValue(String::class.java)
                            val price = dishSnap.child("price").getValue(Int::class.java)
                            val quantity = dishSnap.child("quantity").getValue(Int::class.java)
                            val totalPrice = dishSnap.child("totalPrice").getValue(Int::class.java)
                            if(dishImage != null && dishName != null && price != null && quantity != null && totalPrice != null){
                                val dishData = historyCartDetailsData(dishImage, dishName, price, quantity, totalPrice)
                                dishesList.add(dishData)
                            }
                        }
                    }
                    val mAdapter = historyCartDetailsAdapter(dishesList)
                    detailsRecyclerView.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setValuesToViews(){
        total.text = "${intent.getDoubleExtra("finalTotalPrice",0.0).toString()}$"
        date.text = intent.getStringExtra("date")
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("h:mm a",Locale.getDefault())
        val timeFormated = inputFormat.parse(intent.getStringExtra("time"))
        time.text = outputFormat.format(timeFormated)
        val subtotalInt = intent.getDoubleExtra("finalTotalPrice",0.0) - 5.25 - 1.33
        subtotal.text = "${subtotalInt.toString()}$"
    }

    fun getUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.wagbat.USER_ID_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", "")
    }
}