package com.example.wagbat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter(private var itemlist: ArrayList<item>):
    RecyclerView.Adapter<adapter.MyViewHolder>()  {

    var onItemClick: ((item) -> Unit)?=null

    fun setfilterdlist(itemlist: ArrayList<item>)
    {
        this.itemlist=itemlist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):adapter.MyViewHolder{
    val item_card=LayoutInflater.from(parent.context).inflate(R.layout.item_menu,parent,false)
        return MyViewHolder(item_card)
  }

    override fun onBindViewHolder(holder:adapter.MyViewHolder, position: Int) {
        val current_item = itemlist[position]
        holder.img.setImageResource(current_item.resturant_img)
        holder.name.text = current_item.resturant_nam

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(current_item)
        }
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var img :ImageView = item.findViewById(R.id.resturant_logo)
        var name :TextView = item.findViewById(R.id.resturant_name)
    }


}