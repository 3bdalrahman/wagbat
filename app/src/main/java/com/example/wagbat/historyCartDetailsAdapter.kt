package com.example.wagbat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class historyCartDetailsAdapter(private val details: ArrayList<historyCartDetailsData>):
    RecyclerView.Adapter<historyCartDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyCartDetailsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_cart_card,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: historyCartDetailsAdapter.ViewHolder, position: Int) {
        val currentDish = details[position]
        holder.dishName.text = currentDish.dishName
        holder.dishPrice.text = "${currentDish.price.toString()}$"
        holder.dishQuantity.text = currentDish.quantity.toString()
        holder.dishTotalPrice.text = "${currentDish.totalPrice.toString()}$"
        holder.dishImage.setImageResource(currentDish.dishImage.toString().toInt())
    }

    override fun getItemCount(): Int {
        return details.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dishName : TextView = itemView.findViewById(R.id.dish_name)
        val dishImage : ImageView = itemView.findViewById(R.id.dish_logo)
        val dishPrice : TextView = itemView.findViewById(R.id.dish_price)
        val dishTotalPrice : TextView = itemView.findViewById(R.id.dish_total_price)
        val dishQuantity : TextView = itemView.findViewById(R.id.count)
    }
}