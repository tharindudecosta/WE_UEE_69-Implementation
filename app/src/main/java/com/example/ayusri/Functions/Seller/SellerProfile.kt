package com.example.ayusri.Functions.Seller

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

class SellerProfile : AppCompatActivity() {

    private lateinit var etdname: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var etdShop: EditText
    private lateinit var dupbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_profile)

        val global = UserGlobal.getInstance()

        etdname = findViewById(R.id.sellerName)
        etdemail = findViewById(R.id.sellerEmail)
        etdcontactnum = findViewById(R.id.sellerPhone)
        etdAddress = findViewById(R.id.sellerAddress)
        etdPassword = findViewById(R.id.sellerPass)
        etdShop = findViewById(R.id.sellerShop)

        dupbtn = findViewById(R.id.btnUpdateProfile)

        etdname.setText(global.fullName)
        etdemail.setText(global.email)
        etdcontactnum.setText(global.phoneNo)
        etdAddress.setText(global.address)
        etdPassword.setText(global.password)
        etdShop.setText(global.storeName)

        dupbtn.setOnClickListener {
            saveSellerData()
        }
    }


    private fun saveSellerData() {
        val sellerName = etdname.text.toString()
        val sellerEmail = etdemail.text.toString()
        val sellerPhone = etdcontactnum.text.toString()
        val sellerAdd = etdAddress.text.toString()
        val sellerPass = etdPassword.text.toString()
        val sellerShop = etdShop.text.toString()
//validation
        if (sellerName.isEmpty()) {
            etdname.error = "Please enter name"
        }
        if (sellerEmail.isEmpty()) {
            etdemail.error = "Please enter Email"
        }
        if (sellerPhone.isEmpty()) {
            etdcontactnum.error = "Please enter Phone Number"
        }
        if (sellerAdd.isEmpty()) {
            etdAddress.error = "Please enter Address"
        }
        if (sellerPass.isEmpty()) {
            etdAddress.error = "Please enter Password"
        }
        if (sellerShop.isEmpty()) {
            etdShop.error = "Please enter shop name"
        } else {
            val global = UserGlobal.getInstance()

            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(global.id.toString())
            val sellerInfo = UserData(
                id = global.id,
                fullName = sellerName,
                phoneNo = sellerPhone,
                email = sellerEmail,
                address = sellerAdd,
                password = sellerPass,
                userType = "Seller",
                storeName = sellerShop
            )
            if (dbRef != null) {
                dbRef.setValue(sellerInfo)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                        global.fullName = sellerName
                        global.phoneNo = sellerPhone
                        global.email = sellerEmail
                        global.address = sellerAdd
                        global.userType = "Doctor"
                        global.storeName = sellerShop

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