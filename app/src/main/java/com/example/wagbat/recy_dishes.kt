package com.example.wagbat

import MyAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class recy_dishes : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var product_list: ArrayList<dishs>
    lateinit var img: Array<Int>
    lateinit var nam: Array<String>
    lateinit var pric: Array<String>
    lateinit var adapter : MyAdapter
    lateinit var name: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recy_dishes)

        val details= intent.getParcelableExtra<item>("name")
        if(details!=null)
        {
            name = findViewById(R.id.nam)
            val img :ImageView = findViewById(R.id.img)

            name.text = details.resturant_nam
            img.setImageResource(details.resturant_img)
        }

        if(name.text.toString() == "MacDonald's")
        {
            img = arrayOf(
                R.drawable.sand1,
                R.drawable.sand2,
                R.drawable.sad3,
                R.drawable.sand4,
                R.drawable.sand5,
                R.drawable.sand6,
            )
            nam = arrayOf("big tasty","original","combo1","combo2","combo3","combo4")

            pric = arrayOf("150","100","300","350","250", "350")
        }else if(name.text.toString() == "Burger King"){

        img = arrayOf(
            R.drawable.burg1,
            R.drawable.burg2,
            R.drawable.burg3,
            R.drawable.burg4,
            R.drawable.burg5,
            R.drawable.burg6)

            nam = arrayOf("big burger","original","combo1","combo2","chees burger","combo4")

            pric = arrayOf("150","100","300","350","200", "300")
        }else if(name.text.toString() == "Bufallo Burger" || name.text.toString() == "KFC"){

            img = arrayOf(
                R.drawable.baf1,
                R.drawable.baf2,
                R.drawable.baf3,
                R.drawable.baf4,
                R.drawable.baf5,
                R.drawable.baf6)

            nam = arrayOf("big burger","halbino burger","cheese burger","combo2","combo1","combo3")

            pric = arrayOf("200","180","300","250","200", "350")
        }else if(name.text.toString() == "Pizza Hut" || name.text.toString() == "Roma Pizza" ){

            img = arrayOf(
                R.drawable.pizza1,
                R.drawable.pizza2,
                R.drawable.pizza3,
                R.drawable.pizza4,
                R.drawable.pizza6,
                R.drawable.pizza1)

            nam = arrayOf("checkien pizza","chicken ranch","margrita","combo1","combo2","combo3")

            pric = arrayOf("200","180","300","150","170", "160")
        }else if(name.text.toString() == "Mori Sushi" ){

            img = arrayOf(
                R.drawable.su1,
                R.drawable.su2,
                R.drawable.su3,
                R.drawable.su5,
                R.drawable.su6,
                R.drawable.su1)

            nam = arrayOf("combo1","combo2","combo3","combo4","combo5","combo6")

            pric = arrayOf("1000","700","800","650","850", "1200")
        }else if(name.text.toString() == "Shawrma El-reem" ){

            img = arrayOf(
                R.drawable.sha1,
                R.drawable.sha2,
                R.drawable.sha3,
                R.drawable.sha4,
                R.drawable.shaw5,
                R.drawable.sha6)

            nam = arrayOf("chicken","meat ","meat with cola","chickn","combo1","combo2")

            pric = arrayOf("60","80","100","50","150", "160")
        }else if(name.text.toString() == "El-tahrer" || name.text.toString() == "Abo-Tarek" || name.text.toString() == "Elkhedawy"){

            img = arrayOf(
                R.drawable.kosh1,
                R.drawable.kosh2,
                R.drawable.kosh3,
                R.drawable.kosh4,
                R.drawable.kosh5,
                R.drawable.kosh6)

            nam = arrayOf("small","medium ","large","x large","small with cola","large with cola")

            pric = arrayOf("25","35","50","70","60", "100")
        }else{

            img = arrayOf(
                R.drawable.past1,
                R.drawable.pasta2,
                R.drawable.pasta4,
                R.drawable.pasta5,
                R.drawable.pasta6,
                R.drawable.past1)

            nam = arrayOf("white sauce","red sauce ","pasta meat","pasta chicken","pasta with veg","Combo")

            pric = arrayOf("100","80","150","130","110","180")
        }
        recycler = findViewById(R.id.catagory2)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        product_list= ArrayList<dishs>()
        getData()

        adapter = MyAdapter(product_list)
        recycler.adapter = adapter

        adapter.onItemClick = {

            val next_page= Intent(this,Dishdetails::class.java)
            next_page.putExtra("name",it)
            startActivity(next_page)
        }
    }



    private fun getData()
    {
        for(i in img.indices)
        {
            val product= dishs(img[i],nam[i],pric[i])
            product_list.add(product)
        }
        recycler.adapter= MyAdapter(product_list)
    }

}

