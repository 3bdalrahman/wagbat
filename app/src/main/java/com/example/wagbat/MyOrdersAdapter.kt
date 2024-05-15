package com.example.wagbat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class MyOrdersAdapter(private val historyList: ArrayList<MyordersData>):
    RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_my_orders_card,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyOrdersAdapter.ViewHolder, position: Int) {
        val currentCart = historyList[position]
        holder.date.text = currentCart.date
        holder.time.text = currentCart.time
        holder.price.text = "${currentCart.finalTotalPrice.toString()}$"
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        val date : TextView = itemView.findViewById(R.id.cart_date)
        val time : TextView = itemView.findViewById(R.id.cart_time)
        val price: TextView = itemView.findViewById(R.id.cart_price)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}