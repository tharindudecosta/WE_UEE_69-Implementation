package com.example.ayusri.Functions.Seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.ayusri.Functions.Doctors.DoctorFetchAdmin
import com.example.ayusri.R

class SellerLoginHome : AppCompatActivity() {

    private lateinit var btnProducts: ImageButton;
    private lateinit var btnProfile: ImageButton
    private lateinit var btnMyOrders: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_login_home)

        btnProducts = findViewById(R.id.btnProducts)
        btnProfile = findViewById(R.id.btnMyProfile)
        btnMyOrders =  findViewById(R.id.btnMyOrders)

        btnProducts.setOnClickListener {
            btnProducts.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, SellerProductsFetch::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnProducts.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.tile_button)
                }
            }.start()
        }

        btnProfile.setOnClickListener {
            btnProfile.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, SellerProfile::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnProfile.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.tile_button)
                }
            }.start()
        }

        btnMyOrders.setOnClickListener {
            btnMyOrders.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, SellerOrdersList::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnMyOrders.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.tile_button)
                }
            }.start()
        }

    }
}