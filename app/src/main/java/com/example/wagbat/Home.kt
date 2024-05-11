package com.example.wagbat

import Homee
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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

    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val user_name = intent.getStringExtra("name")
        val user_email = intent.getStringExtra("Email")




        drawerLayout = findViewById(R.id.drawer_layout)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Homee())
                .commit()
        }

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            onNavigationItemSelected(
                menuItem
            )
        }

        val headerView = navigationView.getHeaderView(0)
        val userTextView = headerView.findViewById<TextView>(R.id.userName)
        val emailTextView = headerView.findViewById<TextView>(R.id.userEmail)

        userTextView.text = user_name
        emailTextView.text = user_email


        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.bringToFront()


    }




    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyProfile())
                    .commit()
            }
            R.id.nav_orders -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyOrders())
                    .commit()
            }
            R.id.nav_FAQs -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FAQs())
                    .commit()
            }
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Homee())
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)

        }else{

            super.onBackPressed()

        }
    }



}






