package com.example.ayusri.Functions.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.ayusri.Functions.Customers.CustomerProductsFetch
import com.example.ayusri.Functions.Doctors.DoctorFetchAdmin
import com.example.ayusri.Functions.Seller.SellerFetchAdmin
import com.example.ayusri.R

class AdminLoginMain : AppCompatActivity() {

    private lateinit var btnDoc: ImageButton
    private lateinit var btnSellers: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_main)

        btnDoc = findViewById(R.id.btnDoctors)
        btnSellers = findViewById(R.id.btnSellers)

        btnDoc.setOnClickListener{
            btnDoc.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, DoctorFetchAdmin::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnDoc.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }
        btnSellers.setOnClickListener {
            btnSellers.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, SellerFetchAdmin::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnSellers.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }
    }
}