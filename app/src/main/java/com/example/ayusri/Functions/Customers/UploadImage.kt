package com.example.ayusri.Functions.Customers

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadImage : AppCompatActivity() {

    private lateinit var storageReference: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var uploadBtn: Button
    private lateinit var showAllBtn:Button
    private lateinit var imageUploadView:ImageView
    private lateinit var progressBar:ProgressBar
    private lateinit var tvUploadTitle:EditText

    val global = UserGlobal.getInstance()

    private var imgUri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_upload_image)

        storageReference = FirebaseStorage.getInstance().reference.child("Images")
        firestore = FirebaseFirestore.getInstance()

        uploadBtn = findViewById(R.id.uploadBtn)
        showAllBtn = findViewById(R.id.showAllBtn)

        imageUploadView = findViewById(R.id.imageUploadView)

        progressBar = findViewById(R.id.progressBar)

        tvUploadTitle = findViewById(R.id.tvUploadTitle)

        showAllBtn.setOnClickListener {
            val i = Intent(this, MyUploads::class.java)
            startActivity(i)
        }

        imageUploadView.setOnClickListener {
            resultLaunch.launch("image/*")
        }

        uploadBtn.setOnClickListener {
            uploadImage()
        }
    }

    private val resultLaunch = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        imgUri = it
        imageUploadView.setImageURI(it)
    }

    private fun uploadImage(){
        progressBar.visibility = View.VISIBLE
        storageReference = storageReference.child(System.currentTimeMillis().toString())
        if(imgUri ==null){
            Toast.makeText(this,"Upload Image",Toast.LENGTH_SHORT).show()
        }else{
            imgUri?.let {
                storageReference.putFile(it).addOnCompleteListener{ task->
                    if (task.isSuccessful){

                        storageReference.downloadUrl.addOnSuccessListener { uri->
                            val map = HashMap<String,Any>()
                            map["pic"] = uri.toString()
                            map["patientId"] = global.id.toString()
                            map["uploadTitle"] = tvUploadTitle.text.toString()

                            firestore.collection("images").add(map).addOnCompleteListener { firestoreTask->
                                if (firestoreTask.isSuccessful){
                                    Toast.makeText(this,"Ok",Toast.LENGTH_LONG).show()
                                    tvUploadTitle.text.clear()
                                }
                                else{
                                    Toast.makeText(this,firestoreTask.exception?.message,Toast.LENGTH_LONG).show()
                                }
                                progressBar.visibility = View.GONE
                                imageUploadView.setImageResource(R.drawable.vector)
                            }
                        }
                    }else{
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        imageUploadView.setImageResource(R.drawable.vector)
                    }
                }
            }

        }
    }

}