package com.example.ayusri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class test1 : AppCompatActivity() {

    private lateinit var tvDocName: TextView
    private lateinit var tvDocEmail: TextView
    private lateinit var tvDocPhone: TextView
    private lateinit var tvDocHospital: TextView
    private lateinit var tvDocAddress: TextView

    //new



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_test1)







        initView()
        setValueToViews()


    }




    private fun setValueToViews() {

        tvDocName.text = intent.getStringExtra("docName")
        tvDocEmail.text = intent.getStringExtra("docEmail")
        tvDocPhone.text = intent.getStringExtra("docPhone")
        tvDocHospital.text = intent.getStringExtra("docHospital")
        tvDocAddress.text = intent.getStringExtra("docAddress")
    }

    private fun initView() {

        tvDocName = findViewById(R.id.tvDocName)
        tvDocEmail = findViewById(R.id.tvDocEmail)
        tvDocPhone = findViewById(R.id.tvDocPhone)
        tvDocHospital = findViewById(R.id.tvDocHos)
        tvDocAddress = findViewById(R.id.tvDocAddress)



    }
}