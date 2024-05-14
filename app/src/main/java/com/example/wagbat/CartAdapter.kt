package com.example.wagbat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class CartAdapter(private val cartList :ArrayList<CartItem>
    ,private val userId: String
    ,private val cartDishIds: ArrayList<String>
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_card,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val currentItem = cartList[position]
            val cartDishId = cartDishIds[position]
            holder.dishImage.setImageResource(currentItem.dishImage.toString().toInt())
            holder.dishName.text = currentItem.dishName
            holder.dishTotalPrice.text = "${currentItem.totalPrice}$" // Set total price text
            var totalPriceInt: Int = currentItem.totalPrice

            val firstPrice: Int = currentItem.price

            holder.addBtn.setOnClickListener {
                if (currentItem.quantity > 0) {
                    currentItem.quantity++
                    totalPriceInt += firstPrice
                    currentItem.totalPrice = totalPriceInt
                    updateCartItem(currentItem, cartDishId, totalPriceInt)
                    notifyItemChanged(position)
                    Log.d("data", currentItem.totalPrice.toString())
                }
            }

            holder.removeBtn.setOnClickListener {
                if (currentItem.quantity > 1) {
                    currentItem.quantity--
                    totalPriceInt -= firstPrice
                    currentItem.totalPrice = totalPriceInt
                    updateCartItem(currentItem, cartDishId, totalPriceInt)
                    notifyItemChanged(position)
                    Log.d("data", currentItem.totalPrice.toString())
                }
            }
            Log.d("price" , currentItem.price.toString())
            holder.price.text = currentItem.price.toString()
            holder.quantity.text = currentItem.quantity.toString()
        } catch (e: IndexOutOfBoundsException) {
            Log.e("CartAdapter", "IndexOutOfBoundsException: ${e.message}")
        }
    }


    private fun updateCartItem(item: CartItem, cartDishId: String, totalPriceInt: Int) {
        val cartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart").child(cartDishId)
        cartRef.child("quantity").setValue(item.quantity)
        cartRef.child("totalPrice").setValue(totalPriceInt) // Update total price
        Log.d("data", item.totalPrice.toString())
        Log.d("data", item.quantity.toString())
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImage: ImageView = itemView.findViewById(R.id.dish_logo)
        val dishName: TextView = itemView.findViewById(R.id.dish_name)
        val dishTotalPrice: TextView = itemView.findViewById(R.id.dish_total_price) // Added
        val quantity: TextView = itemView.findViewById(R.id.count)
        val addBtn: Button = itemView.findViewById(R.id.add_btn)
        val removeBtn: Button = itemView.findViewById(R.id.remove_btn)
        val price: TextView = itemView.findViewById(R.id.dish_price)
    }
}