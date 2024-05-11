package com.example.wagbat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenuPresenter
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity(){
    lateinit var recycler: RecyclerView
    lateinit var product_list: ArrayList<item>
    lateinit var img_id: Array<Int>
    lateinit var names: Array<String>
    lateinit var search : SearchView
    lateinit var adapter : adapter

    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        NavigationView.OnNavigationItemSelectedListener { menuItem -> onNavigationItemSelected(menuItem) }

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null){

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,MyProfile()).commit()
            navigationView.setCheckedItem(R.id.nav_profile)

        }

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


    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,MyProfile()).commit()
            R.id.nav_orders -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,MyOrders()).commit()
            R.id.nav_FAQs -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,FAQs()).commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)

        }else{

            onBackPressedDispatcher.onBackPressed()

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






