package com.example.wagbat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.findColumnIndexBySuffix
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MyOrders : Fragment() {
    private lateinit var cartsRecyclerView: RecyclerView
    private lateinit var historyList: ArrayList<MyordersData>
    private lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_orders,container,false)
        cartsRecyclerView = view.findViewById(R.id.historytRecycler)
        cartsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartsRecyclerView.setHasFixedSize(true)
        historyList = arrayListOf<MyordersData>()
        userId = getUserId(requireContext()) ?: ""
        getHistoryList()
        // Inflate the layout for this fragment
        return view
    }

    private fun getHistoryList(){
        val currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("history")
        currentUserRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                historyList.clear()
                if(snapshot.exists()){
                    for(cartSnap in snapshot.children){
                        val date = cartSnap.child("details/date").getValue(String::class.java)
                        val time = cartSnap.child("details/time").getValue(String::class.java)
                        val finalTotalPrice = cartSnap.child("finalTotalPrice").getValue(Double::class.java)
                        val cartId = cartSnap.key
                        if(date != null && time != null && finalTotalPrice != null&& cartId!=null){
                            val cartData = MyordersData(cartId,date,time,finalTotalPrice)
                            historyList.add(cartData)
                        }
                    }
                    val myOrdersAdapter = MyOrdersAdapter(historyList)
                    cartsRecyclerView.adapter = myOrdersAdapter

                    myOrdersAdapter.setOnItemClickListener(object : MyOrdersAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(),historyCartDetails::class.java)
                            intent.putExtra("historyCartId",historyList[position].cartId)
                                .putExtra("date",historyList[position].date)
                                .putExtra("time",historyList[position].time)
                                .putExtra("finalTotalPrice",historyList[position].finalTotalPrice)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.wagbat.USER_ID_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", null)
    }
}