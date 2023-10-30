package com.example.ayusri.Functions.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.ayusri.Functions.Customers.CustomerProductsFetch
import com.example.ayusri.Functions.Seller.SellerProductsFetch
import com.example.ayusri.R

class DocLoginHomeActivity : AppCompatActivity() {

    private lateinit var btnDocProfile: ImageButton
    private lateinit var btnDocApp: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_login_home)

        btnDocProfile = findViewById(R.id.btnDocProfile)
        btnDocApp = findViewById(R.id.btnDocApp)

        btnDocProfile.setOnClickListener {
            btnDocProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, DoctorProfile::class.java)
            startActivity(i)
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnDocProfile.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

        btnDocApp.setOnClickListener{

            btnDocApp.background = ContextCompat.getDrawable(applicationContext, R.drawable.tile_button_on_click)
            var i = Intent(this, DoctorAppointments::class.java)
            startActivity(i)

            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    btnDocApp.background = ContextCompat.getDrawable(applicationContext,R.drawable.tile_button)
                }
            }.start()
        }

    }
}