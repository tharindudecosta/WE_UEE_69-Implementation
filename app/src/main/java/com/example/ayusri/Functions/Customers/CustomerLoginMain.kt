package com.example.ayusri.Functions.Customers

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ayusri.Functions.Common.LoginUser
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.android.material.navigation.NavigationView


class CustomerLoginMain : AppCompatActivity() {

    private val global = UserGlobal.getInstance()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewEmail: TextView

    private lateinit var btnShop: ImageButton
    private lateinit var btnDoctors: ImageButton
    private lateinit var btnOrders: ImageButton
    private lateinit var btnAppointments: ImageButton
    private lateinit var btnMyProfile: ImageButton
    private lateinit var btnMyUploads: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login_home)


        btnShop = findViewById(R.id.btnShop)
        btnDoctors = findViewById(R.id.btnDoctors)
        btnOrders = findViewById(R.id.btnOrders)
        btnAppointments = findViewById(R.id.btnAppointments)
        btnMyProfile = findViewById(R.id.btnMyProfile)
        btnMyUploads = findViewById(R.id.btnMyUploads)


        btnShop.setOnClickListener {
            btnShop.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, CustomerProductsFetch::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnShop.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

        btnDoctors.setOnClickListener {
            btnDoctors.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, CustomerDoctorsFetch::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnDoctors.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

        btnMyProfile.setOnClickListener {
            btnMyProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, CustomerProfile::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnMyProfile.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }
        btnMyUploads.setOnClickListener {
            btnMyUploads.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, MyUploads::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnMyUploads.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

        btnAppointments.setOnClickListener {
            btnAppointments.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, MyAppointments::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnAppointments.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

        btnOrders.setOnClickListener {
            btnOrders.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, MyOrders::class.java)
            startActivity(i)
            object : CountDownTimer(900, 40) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnOrders.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            fun login() {
                var intent = Intent(this, LoginUser::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Clicked Login", Toast.LENGTH_SHORT).show()
            }

            fun home() {
                var intent = Intent(this, CustomerLoginMain::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
            }

            fun logout() {
                var intent = Intent(this, LoginUser::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Clicked Logout", Toast.LENGTH_SHORT).show()
            }

            fun changeLan(){

            }



            when (it.itemId) {

                R.id.nav_home -> home()
                R.id.nav_message -> Toast.makeText(
                    applicationContext,
                    "Clicked Message",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_sync -> Toast.makeText(
                    applicationContext,
                    "Clicked Sync",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_trash -> Toast.makeText(
                    applicationContext,
                    "Clicked Delete",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_setting -> Toast.makeText(
                    applicationContext,
                    "Clicked Setting",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_login -> login()
                R.id.nav_share -> Toast.makeText(
                    applicationContext,
                    "Clicked Share",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_rate_us -> Toast.makeText(
                    applicationContext,
                    "Clicked Rate us",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> logout()

            }

            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
