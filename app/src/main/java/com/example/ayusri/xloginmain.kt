package com.example.ayusri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ayusri.Functions.Common.LoginUser
import com.example.ayusri.Functions.Customers.CustomerLoginMain
import com.google.android.material.navigation.NavigationView

class xloginmain : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_loginmain)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var Docbtn = findViewById<Button>(R.id.doctorL)

        Docbtn.setOnClickListener{
            var intent =  Intent(this,LoginAdmin::class.java)
            startActivity(intent)
        }

        var cusmbtn = findViewById<Button>(R.id.customerL)

        cusmbtn.setOnClickListener{
            var intent =  Intent(this, LoginUser::class.java)
            startActivity(intent)
        }


        navView.setNavigationItemSelectedListener {
            fun login(){
                var intent =  Intent(this,xloginmain::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"Clicked Login",Toast.LENGTH_SHORT).show()
            }

            fun home(){
                var intent = Intent(this, CustomerLoginMain::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"Clicked Home",Toast.LENGTH_SHORT).show()
            }

            fun logout(){
                var intent = Intent(this, LoginUser::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"Clicked Home",Toast.LENGTH_SHORT).show()
            }


            when(it.itemId){

                R.id.nav_home -> home()
                R.id.nav_message -> Toast.makeText(applicationContext,"Clicked Message",Toast.LENGTH_SHORT).show()
                R.id.nav_sync -> Toast.makeText(applicationContext,"Clicked Sync",Toast.LENGTH_SHORT).show()
                R.id.nav_trash -> Toast.makeText(applicationContext,"Clicked Delete",Toast.LENGTH_SHORT).show()
                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting",Toast.LENGTH_SHORT).show()
                R.id.nav_login ->  login()
                R.id.nav_share -> Toast.makeText(applicationContext, "Clicked Share",Toast.LENGTH_SHORT).show()
                R.id.nav_rate_us -> Toast.makeText(applicationContext,"Clicked Rate us",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> logout()

            }

            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }

}

