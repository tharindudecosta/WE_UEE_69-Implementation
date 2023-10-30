package com.example.ayusri.Functions.Customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.UserData
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase

class CustomerProfile : AppCompatActivity() {

    private lateinit var etdname: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var dupbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile)

        val global = UserGlobal.getInstance()

        etdname = findViewById(R.id.cusName)
        etdemail = findViewById(R.id.cusEmail)
        etdcontactnum = findViewById(R.id.cusPhone)
        etdAddress = findViewById(R.id.cusAddress)
        etdPassword = findViewById(R.id.cusPass)
        dupbtn = findViewById(R.id.btnUpdateProfile)

        etdname.setText(global.fullName)
        etdemail.setText(global.email)
        etdcontactnum.setText(global.phoneNo)
        etdAddress.setText(global.address)
        etdPassword.setText(global.password)

        dupbtn.setOnClickListener {
            saveCustomerData()
        }
    }


    private fun saveCustomerData() {
        val cusName = etdname.text.toString()
        val cusEmail = etdemail.text.toString()
        val cusPhone = etdcontactnum.text.toString()
        val cusAdd = etdAddress.text.toString()
        val cusPass = etdPassword.text.toString()
//validation
        if (cusName.isEmpty()) {
            etdname.error = "Please enter name"
        }
        if (cusEmail.isEmpty()) {
            etdemail.error = "Please enter Email"
        }
        if (cusPhone.isEmpty()) {
            etdcontactnum.error = "Please enter Phone Number"
        }
        if (cusAdd.isEmpty()) {
            etdAddress.error = "Please enter address"
        }
        if (cusPass.isEmpty()) {
            etdAddress.error = "Please enter password"
        } else {
            val global = UserGlobal.getInstance()

            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(global.id.toString())
            val cusInfo = UserData(
                id = global.id,
                fullName = cusName,
                phoneNo = cusPhone,
                email = cusEmail,
                address = cusAdd,
                password = cusPass,
                userType = "Customer"
            )
            if (dbRef != null) {
                dbRef.setValue(cusInfo)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                        global.fullName = cusName
                        global.phoneNo = cusPhone
                        global.email = cusEmail
                        global.address = cusAdd
                        global.userType = "Customer"

                        val alertDialog = AlertDialog.Builder(this)
                            .setTitle("Data Updated")
                            .setMessage("Your profile was updated successfully !!!")
                            .setNegativeButton("OK", null)
                            .create()
                        alertDialog.show()
                    }
                    .addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show()
            }

        }
    }

}