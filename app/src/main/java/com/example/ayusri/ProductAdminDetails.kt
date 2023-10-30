package com.example.ayusri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Functions.Seller.SellerProductsFetch
import com.example.ayusri.Models.Products
import com.google.firebase.database.FirebaseDatabase

class ProductAdminDetails : AppCompatActivity() {
    private lateinit var tvDisTopic: TextView
    private lateinit var tvDisAdd: TextView


    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_product_details_admin)

        initView()
        setValueToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("mediID").toString(),
                intent.getStringExtra("mediTopic").toString(),

                )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("mediID").toString()
            )
        }
    }

    private fun deleteRecord(mediID: String) {
        val dbRef =  FirebaseDatabase.getInstance().getReference("Medicines").child(mediID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"data delete", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SellerProductsFetch::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error-> Toast.makeText(this,"Error ${error.message}", Toast.LENGTH_LONG).show() }
    }

    private fun openUpdateDialog(mediID: String, mediTopic: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.xupdatedis_dialog,null)

        mDialog.setView(mDialogView)
        val etDisTopic = mDialogView.findViewById<EditText>(R.id.diseasetopic)
        val etDis = mDialogView.findViewById<EditText>(R.id.diseasedis)



        val btnupdate = mDialogView.findViewById<Button>(R.id.diseaseupbtn)

        etDisTopic.setText( intent.getStringExtra("mediTopic").toString())
        etDis.setText( intent.getStringExtra("mediAdd").toString())


        mDialog.setTitle("Updating $mediTopic Recod")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnupdate.setOnClickListener{
            updateEmpData(
                mediID,
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
        val dbRef = FirebaseDatabase.getInstance().getReference("Medicines").child(disId)
        val disInfo = Products(disId,topic,dis)
        dbRef.setValue(disInfo)
    }

    private fun setValueToViews() {

        tvDisTopic.text = intent.getStringExtra("productTopic")
        tvDisAdd.text = intent.getStringExtra("productDesc")


    }

    private fun initView() {

        tvDisTopic = findViewById(R.id.tvdisTopic)
        tvDisAdd = findViewById(R.id.tvDisAdd)



        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
}