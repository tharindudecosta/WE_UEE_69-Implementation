package com.example.ayusri.Functions.Seller

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SellerProductUpdate : AppCompatActivity() {


    private lateinit var etProductName: EditText;
    private lateinit var etProductPrice: EditText;
    private lateinit var etProductDescription: EditText
    private lateinit var btnMedicineAdd: Button
    private lateinit var btnUploadImage: Button
    private lateinit var tvImageName: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var dbRef: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var imgUri: Uri? = null
    private var imgUriOld: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_update_seller)


        etProductName = findViewById(R.id.productName)
        etProductPrice = findViewById(R.id.productPrice)
        etProductDescription = findViewById(R.id.productDescription)
        btnMedicineAdd = findViewById(R.id.btnMedicineAdd)
        btnUploadImage = findViewById(R.id.btnUploadImage)
        tvImageName = findViewById(R.id.tvImageName)
        progressBar = findViewById(R.id.progressBar)

        val productId = intent.getStringExtra("productId")
        val productName = intent.getStringExtra("productName")
        val productDesc = intent.getStringExtra("productDesc")
        val productPrice = intent.getStringExtra("productPrice")
        val productImg = intent.getStringExtra("productImg")

        etProductName.setText(productName.toString())
        etProductPrice.setText(productPrice.toString())
        etProductDescription.setText(productDesc.toString())
        imgUriOld = Uri.parse(productImg.toString())

        storageReference = FirebaseStorage.getInstance().reference.child("Images")


        btnMedicineAdd.setOnClickListener {

            if (imgUri != null) {
                saveProdData(productId, imgUri!!)
            } else if (imgUriOld != null){
                saveProdData2(productId)
            }
            else {
                tvImageName.text = "Please upload image"
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


    private fun saveProdData(productId: String?, imgUri: Uri) {
        //getting values
        val productName = etProductName.text.toString()
        val productPrice = etProductPrice.text.toString()
        val productDescription = etProductDescription.text.toString()

//validation
        if (productName.isEmpty()) {
            etProductName.error = "Please enter name"
        }
        if (productPrice.isEmpty()) {
            etProductPrice.error = "Please enter price"
        }
        if (productDescription.isEmpty()) {
            etProductDescription.error = "Please enter description"
        } else {

            val global = UserGlobal.getInstance()

            progressBar.visibility = View.VISIBLE

            val dbRef =
                FirebaseDatabase.getInstance().getReference("Products").child(productId.toString())

            imgUri?.let {
                storageReference.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            val Prod = Products(
                                productID = productId,
                                productTopic = productName,
                                productPrice = productPrice,
                                productDesc = productDescription,
                                sellerId = global.id,
                                imageUri = uri.toString()
                            )

                            if (dbRef != null) {
                                dbRef.setValue(Prod)
                                Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()


                                tvImageName.visibility = View.INVISIBLE
                                progressBar.visibility = View.GONE

                                val alertDialog = AlertDialog.Builder(this)
                                    .setTitle("Product Updated")
                                    .setMessage("Product was updated successfully !!!")
                                    .setNegativeButton("OK", null)
                                    .create()
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



//            dbRef.child(prdId).setValue(newProd)
//                .addOnCompleteListener {
//                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
//
//                    etProductName.text.clear()
//                    etProductPrice.text.clear()
//                    etProductDescription.text.clear()
//
//                }
//                .addOnFailureListener { err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                }


        }

    }

    private fun saveProdData2(productId: String?) {
        //getting values
        val productName = etProductName.text.toString()
        val productPrice = etProductPrice.text.toString()
        val productDescription = etProductDescription.text.toString()

//validation
        if (productName.isEmpty()) {
            etProductName.error = "Please enter name"
        }
        if (productPrice.isEmpty()) {
            etProductPrice.error = "Please enter price"
        }
        if (productDescription.isEmpty()) {
            etProductDescription.error = "Please enter description"
        } else {

            val global = UserGlobal.getInstance()

            progressBar.visibility = View.VISIBLE

            val dbRef =
                FirebaseDatabase.getInstance().getReference("Products").child(productId.toString())

            val Prod = Products(
                productID = productId,
                productTopic = productName,
                productPrice = productPrice,
                productDesc = productDescription,
                sellerId = global.id,
                imageUri = imgUriOld.toString()
            )

            if (dbRef != null) {
                dbRef.setValue(Prod)
                Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()


                tvImageName.visibility = View.INVISIBLE
                progressBar.visibility = View.GONE

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Product Updated")
                    .setMessage("Product was updated successfully !!!")
                    .setNegativeButton("OK", null)
                    .create()
                alertDialog.show()

            } else {

                tvImageName.visibility = View.INVISIBLE
                progressBar.visibility = View.GONE

                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
            }


        }

    }

}