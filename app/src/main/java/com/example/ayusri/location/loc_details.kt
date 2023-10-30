package com.example.ayusri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.Location
import com.example.ayusri.location.locfetching
import com.google.firebase.database.FirebaseDatabase

class loc_details : AppCompatActivity() {
    private lateinit var tvDisId: TextView
    private lateinit var tvDisTopic: TextView
    private lateinit var tvDisAdd: TextView
    private lateinit var tvDisloc: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loc_details)

        initView()
        setValueToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("locID").toString(),
                intent.getStringExtra("locName").toString(),

                )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("locID").toString()
            )
        }
    }

    private fun deleteRecord(locID: String) {
        val dbRef =  FirebaseDatabase.getInstance().getReference("Locations").child(locID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"data delete", Toast.LENGTH_LONG).show()
            val intent = Intent(this,locfetching::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error-> Toast.makeText(this,"Error ${error.message}", Toast.LENGTH_LONG).show() }
    }

    private fun openUpdateDialog(locID: String, locName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.xupdateloc_dialog,null)

        mDialog.setView(mDialogView)
        val etDisTopic = mDialogView.findViewById<EditText>(R.id.locname)
        val etDis = mDialogView.findViewById<EditText>(R.id.loclocation)
        val etphone = mDialogView.findViewById<EditText>(R.id.locphone)


        val btnupdate = mDialogView.findViewById<Button>(R.id.locupbtn)

        etDisTopic.setText( intent.getStringExtra("locName").toString())
        etDis.setText( intent.getStringExtra("locLoc").toString())
        etphone.setText( intent.getStringExtra("locPhone").toString())

        mDialog.setTitle("Updating $locName Recod")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnupdate.setOnClickListener{
            updateEmpData(
                locID,
                etDisTopic.text.toString(),
                etDis.text.toString(),
                etphone.text.toString(),

                )
            Toast.makeText(applicationContext,"Doctor Data Update", Toast.LENGTH_LONG).show()

            //we are setting update data to our textview
            tvDisTopic.text = etDisTopic.text.toString()
            tvDisAdd.text = etDis.text.toString()
            tvDisloc.text = etphone.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(locID: String, locName:String, locPhone:String, locLoc:String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Locations").child(locID)
        val disInfo = Location(locID, locName, locLoc, locPhone)
        dbRef.setValue(disInfo)
    }

    private fun setValueToViews() {
        tvDisId .text = intent.getStringExtra("locID")
        tvDisloc.text = intent.getStringExtra("locName")
        tvDisAdd.text = intent.getStringExtra("locLoc")
        tvDisTopic.text = intent.getStringExtra("locPhone")
    }

    private fun initView() {
        tvDisId = findViewById(R.id.tvlocId)
        tvDisloc = findViewById(R.id.tvloc)
        tvDisAdd = findViewById(R.id.tvlocAdd)
        tvDisTopic = findViewById(R.id.tvlocPhone)


        btnUpdate = findViewById(R.id.locbtnUpdate)
        btnDelete = findViewById(R.id.locbtnDelete)
    }
}