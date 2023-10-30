package com.example.ayusri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.Disease
import com.google.firebase.database.FirebaseDatabase

class DisDetailsActivity : AppCompatActivity() {
    private lateinit var tvDisId: TextView
    private lateinit var tvDisTopic: TextView
    private lateinit var tvDisAdd: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_dis_details)

        initView()
        setValueToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("disID").toString(),
                intent.getStringExtra("disTopic").toString(),

                )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("disID").toString()
            )
        }
    }

    private fun deleteRecord(disId: String) {
        val dbRef =  FirebaseDatabase.getInstance().getReference("Diseases").child(disId)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"data delete", Toast.LENGTH_LONG).show()
            val intent = Intent(this,DiseaseFetching::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error-> Toast.makeText(this,"Error ${error.message}", Toast.LENGTH_LONG).show() }
    }

    private fun openUpdateDialog(disId: String, disTopic: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.xupdatedis_dialog,null)

        mDialog.setView(mDialogView)
        val etDisTopic = mDialogView.findViewById<EditText>(R.id.diseasetopic)
        val etDis = mDialogView.findViewById<EditText>(R.id.diseasedis)



        val btnupdate = mDialogView.findViewById<Button>(R.id.diseaseupbtn)

        etDisTopic.setText( intent.getStringExtra("disTopic").toString())
        etDis.setText( intent.getStringExtra("disAdd").toString())


        mDialog.setTitle("Updating $disTopic Recod")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnupdate.setOnClickListener{
            updateEmpData(
                disId,
                etDisTopic.text.toString(),
                etDis.text.toString(),

                )
            Toast.makeText(applicationContext,"Doctor Data Update", Toast.LENGTH_LONG).show()

            //we are setting update data to our textview
            tvDisTopic.text = etDisTopic.text.toString()
            tvDisAdd.text = etDis.text.toString()


            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(disId: String, topic:String,dis:String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Diseases").child(disId)
        val disInfo = Disease(disId,topic,dis)
        dbRef.setValue(disInfo)
    }

    private fun setValueToViews() {
        tvDisId .text = intent.getStringExtra("disID")
        tvDisTopic.text = intent.getStringExtra("disTopic")
        tvDisAdd.text = intent.getStringExtra("disAdd")

    }

    private fun initView() {
        tvDisId = findViewById(R.id.tvDisId)
        tvDisTopic = findViewById(R.id.tvdisTopic)
        tvDisAdd = findViewById(R.id.tvDisAdd)



        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
}