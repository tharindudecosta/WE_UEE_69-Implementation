package com.example.ayusri.calculate

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ayusri.R


class MedicalMainActivity : AppCompatActivity() {
    private lateinit var tvDisTopic: TextView
    private lateinit var tvPrice: TextView
    private lateinit var Quntity : EditText

   private lateinit var sub :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_main)
       sub = findViewById(R.id.subbutton)
        sub.setOnClickListener{
            var i = Intent(this,ClientSetOrders::class.java)
            startActivity(i)
        }


        initView()
        setValueToViews()
    }


    private fun setValueToViews() {
        tvDisTopic.text = intent.getStringExtra("mediTopic")
        tvPrice.text = intent.getStringExtra("mediAdd")

    }

    private fun initView() {
        tvDisTopic = findViewById(R.id.tvmedicine)
        tvPrice = findViewById(R.id.tvDiscription)

    }



    }
