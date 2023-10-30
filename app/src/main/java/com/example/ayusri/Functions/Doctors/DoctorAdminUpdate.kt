package com.example.ayusri.Functions.Doctors

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DoctorAdminUpdate : AppCompatActivity() {

    private lateinit var etdname: EditText
    private lateinit var etdemail: EditText
    private lateinit var etdcontactnum: EditText
    private lateinit var etdAddress: EditText
    private lateinit var etdPassword: EditText
    private lateinit var spnSpecialization: Spinner
    private lateinit var dregbtn: Button
    private lateinit var btnUploadImage: Button
    private lateinit var tvImageName: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var storageReference: StorageReference

    private var imgUri: Uri? = null
    private var imgUriOld: Uri? = null
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_admin_update)

        etdname = findViewById(R.id.docName)
        etdemail = findViewById(R.id.docEmail)
        etdcontactnum = findViewById(R.id.dcontactnum)
        etdAddress = findViewById(R.id.docAddress)
        etdPassword = findViewById(R.id.docPass)
        spnSpecialization = findViewById(R.id.spnSpecialization)

        btnUploadImage = findViewById(R.id.btnUploadImage)
        tvImageName = findViewById(R.id.tvImageName)
        progressBar = findViewById(R.id.progressBar)

        dregbtn = findViewById(R.id.dregisterbutton)

        val spinnerLayout = findViewById<TextInputLayout>(R.id.spinnerLayout)
        storageReference = FirebaseStorage.getInstance().reference.child("Images")

        dbRef = FirebaseDatabase.getInstance().getReference("users")


        val doctorName = intent.getStringExtra("doctorName")
        val doctorEmail = intent.getStringExtra("doctorEmail")
        val doctorPhone = intent.getStringExtra("doctorPhone")
        val doctorAddress = intent.getStringExtra("doctorAdd")
        val doctorId = intent.getStringExtra("doctorId")
        val doctorSpec = intent.getStringExtra("doctorSpec")
        val doctorPass = intent.getStringExtra("doctorPass")
        val doctorImg = intent.getStringExtra("doctorImg")


        etdname.setText(doctorName)
        etdcontactnum.setText(doctorPhone)
        etdAddress.setText(doctorAddress)
        etdemail.setText(doctorEmail)
        etdPassword.setText(doctorPass)

        imgUriOld = Uri.parse(doctorImg.toString())

        var specArr = resources.getStringArray(R.array.doctorSpecialization)

        spnSpecialization.setSelection(specArr.indexOf(doctorSpec.toString()))

        btnUploadImage.setOnClickListener {
            resultLaunch.launch("image/*")
        }

        dregbtn.setOnClickListener {
            if (spnSpecialization.selectedItemPosition == 0) {
                spinnerLayout.error = "Please select an option"
            } else {
                spinnerLayout.error = null // Clear the error message
            }

            if (imgUri != null) {
                updateDoctorData(doctorId, imgUri!!)
                
            } else if (imgUriOld != null) {
                updateDoctorData2(doctorId)

            } else {
                tvImageName.text = "Please upload image"
                tvImageName.visibility = View.VISIBLE
            }
        }
    }

    private fun updateDoctorData(doctorId: String?, imgUri: Uri) {
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
            etdAddress.error = "Please enter address"
        }
        if (docPass.isEmpty()) {
            etdAddress.error = "Please enter password"
        } else {
            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(doctorId.toString())

            progressBar.visibility = View.VISIBLE

            if (imgUri == null) {
                Toast.makeText(this, "Upload Image", Toast.LENGTH_SHORT).show()
            } else {
                imgUri?.let {
                    storageReference.putFile(it).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            storageReference.downloadUrl.addOnSuccessListener { uri ->

                                val doctor = UserData(
                                    id = doctorId,
                                    fullName = docName,
                                    email = docEmail,
                                    phoneNo = docPhone,
                                    password = docPass,
                                    address = docAdd,
                                    specialization = docSpecialization,
                                    userType = "Doctor",
                                    imageUri = uri.toString()
                                )
                                if (dbRef != null) {
                                    dbRef.setValue(doctor)
                                    Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()


                                    tvImageName.visibility = View.INVISIBLE
                                    progressBar.visibility = View.GONE

                                    val alertDialog = AlertDialog.Builder(this)
                                        .setTitle("Doctor Updated")
                                        .setMessage("Doctor profile was updated successfully !!!")
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

            }

        }
    }


    private fun updateDoctorData2(doctorId: String?) {
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
            etdAddress.error = "Please enter address"
        }
        if (docPass.isEmpty()) {
            etdAddress.error = "Please enter password"
        } else {
            val dbRef =
                FirebaseDatabase.getInstance().getReference("users").child(doctorId.toString())

            progressBar.visibility = View.VISIBLE

            if (imgUriOld == null) {
                Toast.makeText(this, "Upload Image", Toast.LENGTH_SHORT).show()
            } else if (imgUriOld != null) {
                val doctor = UserData(
                    id = doctorId,
                    fullName = docName,
                    email = docEmail,
                    phoneNo = docPhone,
                    password = docPass,
                    address = docAdd,
                    specialization = docSpecialization,
                    userType = "Doctor",
                    imageUri = imgUriOld.toString()
                )
                if (dbRef != null) {
                    dbRef.setValue(doctor)
                    Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()


                    tvImageName.visibility = View.INVISIBLE
                    progressBar.visibility = View.GONE

                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Doctor Updated")
                        .setMessage("Doctor profile was updated successfully !!!")
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

    private val resultLaunch = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imgUri = it
        imgUriOld = null
        tvImageName.text = imgUri.toString()
        tvImageName.visibility = View.VISIBLE
        storageReference = storageReference.child(System.currentTimeMillis().toString())

    }


}