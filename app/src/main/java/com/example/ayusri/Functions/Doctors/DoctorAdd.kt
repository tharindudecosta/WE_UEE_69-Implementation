package com.example.ayusri.Functions.Doctors

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DoctorAdd : AppCompatActivity() {

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
    private lateinit var dbRef: DatabaseReference

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_add)

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
                saveDoctorData()
            } else {
                tvImageName.text = "Please upload image"
                tvImageName.visibility = View.VISIBLE
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
//        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private val resultLaunch = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imgUri = it
        tvImageName.text = imgUri.toString()
        tvImageName.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveDoctorData() {
        //getting values
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
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(docEmail).matches()) {
            etdemail.error = "Please enter valid email address"
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
            val docId = dbRef.push().key!!

//            val doctors = Doctors(id, docId, docName, docEmail, docPhone, docHos,docSpecialization)

            progressBar.visibility = View.VISIBLE
            storageReference = storageReference.child(System.currentTimeMillis().toString())
            imgUri?.let {
                storageReference.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->

                            val doctor = UserData(
                                id = docId,
                                fullName = docName,
                                email = docEmail,
                                phoneNo = docPhone,
                                password = docPass,
                                address = docAdd,
                                specialization = docSpecialization,
                                userType = "Doctor",
                                imageUri = uri.toString()
                            )

                            dbRef.child(docId).setValue(doctor)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this,
                                        "Data inserted successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    etdname.text.clear()
                                    etdemail.text.clear()
                                    etdcontactnum.text.clear()
                                    etdAddress.text.clear()
                                    etdPassword.text.clear()
                                    spnSpecialization.setSelection(0)

                                    tvImageName.visibility = View.INVISIBLE
                                    progressBar.visibility = View.GONE

                                    val alertDialog = AlertDialog.Builder(this)
                                        .setTitle("Doctor Added")
                                        .setMessage("New doctor profile was added successfully !!!")
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


