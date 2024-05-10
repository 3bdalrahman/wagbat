package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class Home : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var product_list: ArrayList<item>
    lateinit var img_id: Array<Int>
    lateinit var names: Array<String>
    lateinit var search : SearchView
    lateinit var adapter : adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        img_id = arrayOf(
            R.drawable.mac,
            R.drawable.king,
            R.drawable.bufalo,
            R.drawable.pizza_hut,
            R.drawable.roma,
            R.drawable.kfc,
            R.drawable.mori,
            R.drawable.shawrma,
            R.drawable.koshary,
            R.drawable.alkhdawy,
            R.drawable.desoky,
            R.drawable.abo_tarek,
        )
        names = arrayOf("MacDonald's","Burger King","Bufallo Burger","Pizza Hut","Roma Pizza",
            "KFC","Mori Sushi","Shawrma El-reem","El-tahrer","Elkhedawy","Desoky & Soda","Abo-Tarek")

        recycler = findViewById(R.id.catagory)
        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        product_list= ArrayList<item>()
        getData()
        adapter = adapter(product_list)
        recycler.adapter = adapter
        search = findViewById(R.id.searchbar)

        adapter.onItemClick = {

            val next_page= Intent(this,recy_dishes::class.java)
            next_page.putExtra("name",it)
            startActivity(next_page)
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)
                return true
            }
        })







    }

    private fun filterlist(query: String?) {
        if(query!=null)
        {
            val filterlist= ArrayList<item>()
            for(i in product_list)
            {
                if(i.resturant_nam.lowercase(Locale.ROOT).contains(query))
                {
                    filterlist.add(i)
                }
            }
            if(filterlist.isEmpty())
            {
                Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show()
            }
            else
            {
                adapter.setfilterdlist(filterlist)
            }
        }
    }


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






