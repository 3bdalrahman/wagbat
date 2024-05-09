package com.example.wagbat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var product_list: ArrayList<item>
    lateinit var img_id: Array<Int>
    lateinit var names: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        img_id = arrayOf(
            R.drawable.features,
            R.drawable.features,
            R.drawable.features,
            R.drawable.features,
            R.drawable.features
        )
        names = arrayOf("Burger King","Dunkin Dounts","Piza King","Sandwichs","Roma")

        recycler = findViewById(R.id.catagory)
        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        product_list= ArrayList<item>()
        getData()}


    private fun getData()
    {
        for(i in img_id.indices)
        {
            val product= item(img_id[i],names[i])
            product_list.add(product)
        }
        recycler.adapter= adapter(product_list)
    }
}

