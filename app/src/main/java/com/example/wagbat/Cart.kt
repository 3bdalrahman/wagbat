package com.example.wagbat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date

class Cart : AppCompatActivity() {
    private lateinit var currentUserRef: DatabaseReference
    private lateinit var recylerView: RecyclerView
    private lateinit var cartList: ArrayList<CartItem>
    private var subTotal: Int = 0
    private lateinit var subTotalText :TextView
    private var total: Double = 0.0
    private lateinit var totalText: TextView
    private lateinit var confirmBtn: Button
    private  var finalTotalPrice: Double =0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        val userId = getUserId(this).toString()
        recylerView = findViewById(R.id.dish_checkout)
        subTotalText = findViewById(R.id.subTotal)
        totalText = findViewById(R.id.finalTotalPrice)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.setHasFixedSize(true)
        cartList = arrayListOf<CartItem>()
        getCartData(userId)
//        Log.d("userID",userId.toString())
        confirmBtn = findViewById(R.id.confirmBtn)
        val currentTimeMillis = System.currentTimeMillis()
        val deliveryTimeMllis = calculateDeliveryTime(currentTimeMillis,confirmBtn)
        val deliveryStatus = "preparing"
        confirmBtn.setOnClickListener{
            var intent = Intent(this, Track::class.java)
            intent.putExtra("startTime",currentTimeMillis).putExtra("deliverTime",deliveryTimeMllis).putExtra("finalTotalPrice",finalTotalPrice)
            updateOrderStatus(userId,deliveryStatus)
            startActivity(intent)
            finish()
        }
    }

    private fun calculateDeliveryTime(currentTimeMillis: Long,confirmBtn :Button): Long{
        val currentTime = Calendar.getInstance()
        currentTime.timeInMillis = currentTimeMillis
        val deliveryTime = Calendar.getInstance()
        Log.d("CurrentHour", "${currentTime.get(Calendar.HOUR_OF_DAY)}")
        Log.d("CurrentMinute", "${currentTime.get(Calendar.MINUTE)}")
        if(currentTime.get(Calendar.HOUR_OF_DAY) < 10 || (currentTime.get(Calendar.HOUR_OF_DAY) == 10 && currentTime.get(Calendar.MINUTE) <= 30) ){
            Log.d("DeliveryTime", "Setting delivery time to 12:00 PM")
            deliveryTime.set(Calendar.HOUR_OF_DAY,12)//12:00 PM
            deliveryTime.set(Calendar.MINUTE,0)
            deliveryTime.set(Calendar.SECOND,0)
            return deliveryTime.timeInMillis
        }else if (currentTime.get(Calendar.HOUR_OF_DAY) < 12 || (currentTime.get(Calendar.HOUR_OF_DAY) == 12 && currentTime.get(Calendar.MINUTE) < 30)){
            Log.d("DeliveryTime", "Setting delivery time to 3:00 PM")
            deliveryTime.set(Calendar.HOUR_OF_DAY,15)//3:00 PM
            deliveryTime.set(Calendar.MINUTE,0)
            deliveryTime.set(Calendar.SECOND,0)
            return deliveryTime.timeInMillis
        }else{
//            confirmBtn.isEnabled = false
            Log.d("DeliveryTime", "Setting default delivery time to 9:00 PM")
            deliveryTime.set(Calendar.HOUR_OF_DAY,21)//9:00 PM
            deliveryTime.set(Calendar.MINUTE,0)
            Toast.makeText(this@Cart,"Orders can be confirmed before 1:30 pm",Toast.LENGTH_SHORT).show()
            deliveryTime.set(Calendar.SECOND,0)
            return deliveryTime.timeInMillis
        }
    }

    private fun getCartData(userId: String) {
        val currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart")
        currentUserRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                cartList.clear()
                subTotal = 0
                if (snapshot.exists()){
                    val cartDishIds = ArrayList<String>()
                    for(cartSnapshot in snapshot.children){
                        if (cartSnapshot.key != "date" && cartSnapshot.key != "time" && cartSnapshot.key != "status") {
                            val item = cartSnapshot.getValue(CartItem::class.java)
                            item?.let{
                                cartList.add(it)
                                subTotal+= it.totalPrice
                                cartDishIds.add(cartSnapshot.key!!).toString()
                                Log.d("cartDishIds", cartDishIds.toString())
                            }
                        }
                    }
                    subTotalText.text = "${subTotal}$"
                    total = subTotal + 5.25 + 1.33
                    totalText.text = "${total}$"
                    finalTotalPrice = total
                    val cartAdapter = CartAdapter(cartList,userId,cartDishIds)
                    Log.d("finalTotalPrice",finalTotalPrice.toString())
                    recylerView.adapter = cartAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("database","cancelled")
                Toast.makeText(this@Cart, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.wagbat.USER_ID_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", null)
    }

    fun updateOrderStatus(userId: String,status: String){
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val order = mapOf(
            "status" to status,
            "date" to currentDate,
            "time" to currentTime
        )
        val database = FirebaseDatabase.getInstance()
        val orderRef = database.getReference("users").child(userId).child("cart")
        orderRef.updateChildren(order)
            .addOnSuccessListener {
                // Handle success
                println("Order status updated successfully")
            }
            .addOnFailureListener { exception ->
                // Handle failure
                println("Failed to update order status: $exception")
            }
    }

}