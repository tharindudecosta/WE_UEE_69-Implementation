package com.example.ayusri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Functions.Customers.CustomerDoctorsFetch
import com.example.ayusri.Models.Doctors
import com.google.firebase.database.FirebaseDatabase

class DocDetailsActivity : AppCompatActivity() {
    private lateinit var tvDocId: TextView
    private lateinit var tvDocName: TextView
    private lateinit var tvDocEmail: TextView
    private lateinit var tvDocPhone: TextView
    private lateinit var tvDocHospital: TextView
    private lateinit var tvDocAddress: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_doc_details)

        initView()
        setValueToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("docID").toString(),
                intent.getStringExtra("docName").toString(),

                )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("docID").toString()
            )
        }
    }

    private fun deleteRecord(docID: String) {
        val dbRef =  FirebaseDatabase.getInstance().getReference("Doctors").child(docID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Doctor data delete",Toast.LENGTH_LONG).show()
            val intent = Intent(this, CustomerDoctorsFetch::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error-> Toast.makeText(this,"Error ${error.message}",Toast.LENGTH_LONG).show() }
    }

    private fun openUpdateDialog(docId: String, docName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.xupdatedoc_dialog,null)

        mDialog.setView(mDialogView)
        val etDocName = mDialogView.findViewById<EditText>(R.id.dcname)
        val etDocEmail = mDialogView.findViewById<EditText>(R.id.dcmail)
        val etDocPhone = mDialogView.findViewById<EditText>(R.id.dcphone)
        val etDocHos = mDialogView.findViewById<EditText>(R.id.dchos)
        val etDocAddress = mDialogView.findViewById<EditText>(R.id.dcadd)


        val btnupdate = mDialogView.findViewById<Button>(R.id.dcupbtn)

        etDocName.setText( intent.getStringExtra("docName").toString())
        etDocEmail.setText( intent.getStringExtra("docEmail").toString())
        etDocPhone.setText( intent.getStringExtra("docPhone").toString())
        etDocHos.setText( intent.getStringExtra("docHospital").toString())
        etDocAddress.setText( intent.getStringExtra("docAddress").toString())

        mDialog.setTitle("Updating $docName Recod")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnupdate.setOnClickListener{
            updateEmpData(
                docId,
                etDocName.text.toString(),
                etDocEmail.text.toString(),
                etDocPhone.text.toString(),
                etDocHos.text.toString(),
                etDocAddress.text.toString()
            )
            Toast.makeText(applicationContext,"Doctor Data Update",Toast.LENGTH_LONG).show()

            //we are setting update data to our textview
            tvDocName.text = etDocName.text.toString()
            tvDocEmail.text = etDocEmail.text.toString()
            tvDocPhone.text = etDocPhone.text.toString()
            tvDocHospital.text = etDocHos.text.toString()
            tvDocAddress.text = etDocAddress.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(docID: String, docName:String,docEmail:String,docPhone:String,docHospital:String,docAddress: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Doctors").child(docID)
        val docInfo = Doctors(docID,docName,docEmail,docPhone,docHospital,docAddress)
        dbRef.setValue(docInfo)
    }

    private fun setValueToViews() {
        tvDocId .text = intent.getStringExtra("docID")
        tvDocName.text = intent.getStringExtra("docName")
        tvDocEmail.text = intent.getStringExtra("docEmail")
        tvDocPhone.text = intent.getStringExtra("docPhone")
        tvDocHospital.text = intent.getStringExtra("docHospital")
        tvDocAddress.text = intent.getStringExtra("docAddress")
    }

    private fun initView() {
        tvDocId = findViewById(R.id.tvDocId)
        tvDocName = findViewById(R.id.tvDocName)
        tvDocEmail = findViewById(R.id.tvDocEmail)
        tvDocPhone = findViewById(R.id.tvDocPhone)
        tvDocHospital = findViewById(R.id.tvDocHos)
        tvDocAddress = findViewById(R.id.tvDocAddress)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
}