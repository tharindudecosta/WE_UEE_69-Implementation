package com.example.ayusri.Functions.Doctors

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ayusri.Models.UserData
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class DoctorProfile : AppCompatActivity() {

    private lateinit var etdname: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var spnSpecialization: Spinner
    private lateinit var dupbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)

        val global = UserGlobal.getInstance()

        val spinnerLayout = findViewById<TextInputLayout>(R.id.spinnerLayout)

        etdname = findViewById(R.id.docName)
        etdemail = findViewById(R.id.docEmail)
        etdcontactnum = findViewById(R.id.docPhone)
        etdAddress = findViewById(R.id.docAddress)
        etdPassword = findViewById(R.id.docPass)
        spnSpecialization = findViewById(R.id.spnSpecialization)
        dupbtn = findViewById(R.id.btnUpdateProfile)

        etdname.setText(global.fullName)
        etdemail.setText(global.email)
        etdcontactnum.setText(global.phoneNo)
        etdAddress.setText(global.address)
        etdPassword.setText(global.password)

        var specArr = resources.getStringArray(R.array.doctorSpecialization)
        spnSpecialization.setSelection(specArr.indexOf(global.specialization.toString()))

        dupbtn.setOnClickListener {
            if (spnSpecialization.selectedItemPosition == 0) {
                spinnerLayout.error = "Please select an option"
            } else {
                spinnerLayout.error = null // Clear the error message
            }
            saveDoctorData()
        }
    }

    private fun saveDoctorData() {
        val docName = etdname.text.toString()
        val docEmail = etdemail.text.toString()
        val docPhone = etdcontactnum.text.toString()
        val docAdd = etdAddress.text.toString()
        val docPass = etdPassword.text.toString()
        val docSpecialization = spnSpecialization.selectedItem.toString()
//validation
        if (docName.isEmpty()) {
            etdname.error = "Please enter name"
        }
        if (docEmail.isEmpty()) {
            etdemail.error = "Please enter Email"
        }
        if (docPhone.isEmpty()) {
            etdcontactnum.error = "Please enter Phone Number"
        }
        if (docAdd.isEmpty()) {
            etdAddress.error = "Please enter Address"
        }
        if (docPass.isEmpty()) {
            etdAddress.error = "Please enter Password"
        } else {
            val global = UserGlobal.getInstance()

            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(global.id.toString())
            val docInfo = UserData(
                id = global.id,
                fullName = docName,
                phoneNo = docPhone,
                email = docEmail,
                address = docAdd,
                password = docPass,
                userType = "Doctor",
                specialization = docSpecialization,
                imageUri = global.imageUri

            )
            if (dbRef != null) {
                dbRef.setValue(docInfo)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                        global.fullName = docName
                        global.phoneNo = docPhone
                        global.email = docEmail
                        global.address = docAdd
                        global.userType = "Doctor"
                        global.specialization = docSpecialization

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