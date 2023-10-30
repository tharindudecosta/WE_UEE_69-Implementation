package com.example.ayusri.Functions.Seller

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SellerAdd : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var etSellShop: EditText
    private lateinit var dregbtn: Button
    private lateinit var btnUploadImage: Button
    private lateinit var tvImageName: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var dbRef: DatabaseReference

    private lateinit var storageReference: StorageReference

    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_add)

        etName = findViewById(R.id.docName)
        etdemail = findViewById(R.id.docEmail)
        etdcontactnum = findViewById(R.id.dcontactnum)
        etdAddress = findViewById(R.id.docAddress)
        etdPassword = findViewById(R.id.docPass)
        etSellShop = findViewById(R.id.sellShop)
        dregbtn = findViewById(R.id.dregisterbutton)
        btnUploadImage = findViewById(R.id.btnUploadImage)
        tvImageName = findViewById(R.id.tvImageName)
        progressBar = findViewById(R.id.progressBar)

        dbRef = FirebaseDatabase.getInstance().getReference("users")
        storageReference = FirebaseStorage.getInstance().reference.child("Images")

        dregbtn.setOnClickListener {
            if (imgUri != null) {
                saveSellerData()
            } else {
                tvImageName.text = "Please upload image"
                tvImageName.visibility = View.VISIBLE
            }
        }

        btnUploadImage.setOnClickListener {
            resultLaunch.launch("image/*")
        }

    }


    private val resultLaunch = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imgUri = it
        tvImageName.text = imgUri.toString()
        tvImageName.visibility = View.VISIBLE
    }

    private fun saveSellerData() {
        //getting values
        val sellName = etName.text.toString()
        val sellEmail = etdemail.text.toString()
        val sellPhone = etdcontactnum.text.toString()
        val sellAdd = etdAddress.text.toString()
        val sellPass = etdPassword.text.toString()
        val sellShop = etSellShop.text.toString()
//validation
        if (sellName.isEmpty()) {
            etName.error = "Please enter name"
        }
        if (sellEmail.isEmpty()) {
            etdemail.error = "Please enter Email"
        }
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(sellEmail).matches()) {
            etdemail.error = "Please enter valid email address"
        }
        if (sellPhone.isEmpty()) {
            etdcontactnum.error = "Please enter Phone Number"
        }
        if (sellAdd.isEmpty()) {
            etdAddress.error = "Please enter Address "
        }
        if (sellPass.isEmpty()) {
            etdAddress.error = "Please enter Password"
        }
        if (sellShop.isEmpty()) {
            etSellShop.error = "Please enter Shop"
        } else {
            val sellId = dbRef.push().key!!

//            val doctors = Doctors(id, docId, docName, docEmail, docPhone, docHos,docSpecialization)


            progressBar.visibility = View.VISIBLE
            storageReference = storageReference.child(System.currentTimeMillis().toString())
            imgUri?.let {
                storageReference.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->

                            val seller = UserData(
                                id = sellId,
                                fullName = sellName,
                                email = sellEmail,
                                phoneNo = sellPhone,
                                password = sellPass,
                                address = sellAdd,
                                storeName = sellShop,
                                userType = "Seller",
                                imageUri = uri.toString()
                            )

                            dbRef.child(sellId).setValue(seller)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this,
                                        "Data inserted successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    etName.text.clear()
                                    etdemail.text.clear()
                                    etdcontactnum.text.clear()
                                    etdAddress.text.clear()
                                    etdPassword.text.clear()
                                    etSellShop.text.clear()

                                    tvImageName.visibility = View.INVISIBLE
                                    progressBar.visibility = View.GONE

                                    val alertDialog = AlertDialog.Builder(this)
                                        .setTitle("Seller Added")
                                        .setMessage("New seller profile was added successfully !!!")
                                        .setNegativeButton("OK", null)
                                        .create()
                                    alertDialog.show()

                                }
                                .addOnFailureListener { err ->
                                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG)
                                        .show()


                                }

                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE

                    }
                }
            }


        }

    }

}