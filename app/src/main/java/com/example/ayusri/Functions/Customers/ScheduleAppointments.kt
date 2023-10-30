package com.example.ayusri.Functions.Customers

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.ayusri.Models.Appointments
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ScheduleAppointments : AppCompatActivity() {

    private lateinit var etdname: TextView
    private lateinit var etdemail: TextView
    private lateinit var etdcontactnum: TextView
    private lateinit var etdAddress: TextView

    private lateinit var etdDate: EditText
    private lateinit var etdTime: EditText
    private lateinit var appAilment: EditText

    private lateinit var dregbtn: Button

    private lateinit var btnCall: Button

    val global = UserGlobal.getInstance()
    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedDate: Calendar // To store the selected date
    private lateinit var selectedTime: Calendar // To store the selected time

    var REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_schedule_appointments)

        etdname = findViewById(R.id.docName)
        etdemail = findViewById(R.id.docEmail)
        etdcontactnum = findViewById(R.id.dcontactnum)
        etdAddress = findViewById(R.id.docAddress)

        etdDate = findViewById(R.id.appDate)
        etdTime = findViewById(R.id.appTime)
        dregbtn = findViewById(R.id.btnSubmit)
        appAilment = findViewById(R.id.appAilment)

        btnCall = findViewById(R.id.btnCall)

        dbRef = FirebaseDatabase.getInstance().getReference("appointments")


        val doctorName = intent.getStringExtra("doctorName")
        val doctorEmail = intent.getStringExtra("doctorEmail")
        val doctorPhone = intent.getStringExtra("doctorPhone")
        val doctorAddress = intent.getStringExtra("doctorAdd")
        val doctorId = intent.getStringExtra("doctorId")

        etdname.setText(doctorName)
        etdcontactnum.setText(doctorPhone)
        etdAddress.setText(doctorAddress)
        etdemail.setText(doctorEmail)

        selectedDate = Calendar.getInstance()
        selectedTime = Calendar.getInstance()

        etdDate.setOnClickListener {
            openDateDialog()
        }
        etdTime.setOnClickListener {
            showTimePickerDialog()
        }
        dregbtn.setOnClickListener {
            saveAppointment(doctorId, doctorName, doctorEmail, doctorPhone, doctorAddress)
        }
        btnCall.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_PHONE_CALL
                )
            } else {
                makeCall()
            }

        }

    }

    private fun makeCall() {
        val numberText = "0760710380"
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$numberText"))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            return
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL) {
            makeCall()
        }
    }

    private fun saveAppointment(
        doctorId: String?,
        doctorName: String?,
        doctorEmail: String?,
        doctorPhone: String?,
        doctorAddress: String?
    ) {
        val appointmentDate = etdDate.text.toString()
        val appointmentTime = etdTime.text.toString()
        val ailment = appAilment.text.toString()

        val customerId = global.id.toString()
        val customerName = global.fullName.toString()

        if (appointmentDate.isEmpty()) {
            etdname.error = "Please enter Date"
        }
        if (appointmentTime.isEmpty()) {
            etdemail.error = "Please enter Time"
        }
        if (ailment.isEmpty()) {
            appAilment.error = "Please enter Ailment"
        } else {
            val appointmentId = dbRef.push().key!!

            val appointmentNew = Appointments(
                appointmentId = appointmentId,
                docId = doctorId,
                docName = doctorName,
                docEmail = doctorEmail,
                docPhone = doctorPhone,
                docAddress = doctorAddress,
                cusId = customerId,
                cusName = customerName,
                date = appointmentDate,
                time = appointmentTime,
                ailment = ailment
            )

            dbRef.child(appointmentId).setValue(appointmentNew)
                .addOnCompleteListener {
                    Toast.makeText(this, "Appointment Scheduled successfully", Toast.LENGTH_LONG)
                        .show()

                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Success!!!")
                        .setMessage("Appointment Scheduled successfully")
                        .setNegativeButton("OK", null)
                        .create()
                    alertDialog.show()

                    etdTime.text.clear()
                    etdDate.text.clear()
                    appAilment.text.clear()
//                    val intent = Intent(this, CustomerDoctorsFetch::class.java)
//                    startActivity(intent)

                }
                .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

        }


    }

    private fun openDateDialog() {

        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Handle the selected date here
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the UI with the selected date
                // For example, display it in a TextView
                val dateString = "$dayOfMonth/${monthOfYear + 1}/$year"
                // Update a TextView with the selected date
                etdDate.setText(dateString)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    private fun showTimePickerDialog() {
        val hour = selectedTime.get(Calendar.HOUR_OF_DAY)
        val minute = selectedTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                // Handle the selected time here
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                // Update the UI with the selected time
                // For example, display it in a TextView
                val timeString = "$hourOfDay:$minute"
                // Update a TextView with the selected time
                etdTime.setText(timeString)
            },
            hour,
            minute,
            true // Use true if you want 24-hour format, false for 12-hour format
        )
        timePickerDialog.show()
    }
}