package com.example.ayusri.Functions.Seller

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SellerAdminUpdate : AppCompatActivity() {


    private lateinit var etName: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var etSellShop: EditText
    private lateinit var dregbtn: Button
    private lateinit var tvImageName: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnUploadImage: Button

    private lateinit var dbRef: DatabaseReference

    private lateinit var storageReference: StorageReference
    private var imgUri: Uri? = null
    private var imgUriOld: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_list_admin_update)


        etName = findViewById(R.id.docName)
        etdemail = findViewById(R.id.docEmail)
        etdcontactnum = findViewById(R.id.dcontactnum)
        etdAddress = findViewById(R.id.docAddress)
        etdPassword = findViewById(R.id.docPass)
        etSellShop = findViewById(R.id.sellShop)
        dregbtn = findViewById(R.id.dregisterbutton)
        tvImageName = findViewById(R.id.tvImageName)
        progressBar = findViewById(R.id.progressBar)
        btnUploadImage = findViewById(R.id.btnUploadImage)

        val sellerName = intent.getStringExtra("sellerName")
        val sellerEmail = intent.getStringExtra("sellerEmail")
        val sellerPhone = intent.getStringExtra("sellerPhone")
        val sellerAddress = intent.getStringExtra("sellerAdd")
        val sellerId = intent.getStringExtra("sellerId")
        val sellerShop = intent.getStringExtra("sellerShop")
        val sellerPass = intent.getStringExtra("sellerPass")
        val sellerImg = intent.getStringExtra("sellerImg")

        imgUriOld = Uri.parse(sellerImg.toString())


        etName.setText(sellerName)
        etdemail.setText(sellerEmail)
        etdcontactnum.setText(sellerPhone)
        etdAddress.setText(sellerAddress)
        etdPassword.setText(sellerPass)
        etSellShop.setText(sellerShop)

        storageReference = FirebaseStorage.getInstance().reference.child("Images")

        dregbtn.setOnClickListener {
            if (imgUri != null) {
                saveSellerData(sellerId, imgUri!!)
            } else if (imgUriOld != null) {
                saveSellerData2(sellerId)
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


    private fun saveSellerData(sellerId: String?, imageUri: Uri) {
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
            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(sellerId.toString())

//            val doctors = Doctors(id, docId, docName, docEmail, docPhone, docHos,docSpecialization)
            progressBar.visibility = View.VISIBLE

            imgUri?.let {
                storageReference.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            val docInfo = UserData(
                                id = sellerId,
                                fullName = sellName,
                                phoneNo = sellPhone,
                                email = sellEmail,
                                address = sellAdd,
                                userType = "Seller",
                                storeName = sellShop,
                                password = sellPass,
                                imageUri = uri.toString()
                            )

                            if (dbRef != null) {
                                dbRef.setValue(docInfo)
                                Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()


                                tvImageName.visibility = View.INVISIBLE
                                progressBar.visibility = View.GONE

                                val alertDialog =
                                    AlertDialog.Builder(this).setTitle("Seller Updated")
                                        .setMessage("Seller profile was updated successfully !!!")
                                        .setNegativeButton("OK", null).create()
                                alertDialog.show()

                            } else {


                                tvImageName.visibility = View.INVISIBLE
                                progressBar.visibility = View.GONE

                                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
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

    private fun saveSellerData2(sellerId: String?) {
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

//            val doctors = Doctors(id, docId, docName, docEmail, docPhone, docHos,docSpecialization)

            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(sellerId.toString())
            val docInfo = UserData(
                id = sellerId,
                fullName = sellName,
                phoneNo = sellPhone,
                email = sellEmail,
                address = sellAdd,
                userType = "Seller",
                storeName = sellShop,
                imageUri = imgUriOld.toString(),
                password = sellPass
            )
            if (dbRef != null) {
                dbRef.setValue(docInfo)

                val alertDialog = AlertDialog.Builder(this).setTitle("Seller Updated")
                    .setMessage("Seller profile was updated successfully !!!")
                    .setNegativeButton("OK", null).create()
                alertDialog.show()

                Toast.makeText(this, "Seller Updated", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        }

    }

}