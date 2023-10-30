package com.example.ayusri.Functions.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.*

class CreateAccount : AppCompatActivity() {


    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var db: DatabaseReference


    lateinit var Continue: Button
    lateinit var EmailRegister: EditText
    lateinit var PasswordRegister: EditText
    lateinit var Name: EditText
    lateinit var Phone: EditText
    lateinit var cusLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        Continue = findViewById(R.id.registerbutton)
        EmailRegister = findViewById(R.id.cemail)
        PasswordRegister = findViewById(R.id.cpassword1)
        Name = findViewById(R.id.cname)
        Phone = findViewById(R.id.cphone)
        cusLogin = findViewById(R.id.cusLogin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        db = firebaseDatabase.reference.child("users")

        cusLogin.setOnClickListener {
            var intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }

        Continue.setOnClickListener {

            val userEmail = EmailRegister.text.toString()
            val password = PasswordRegister.text.toString()
            val name = Name.text.toString()
            val phone = Phone.text.toString()


            if (userEmail.isEmpty()) {
                EmailRegister.error = "Please enter Email"
            }
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                EmailRegister.error = "Please enter valid email address"
            }
            if (password.isEmpty()) {
                PasswordRegister.error = "Please enter Password"
            }
            if (name.isEmpty()) {
                Name.error = "Please enter Fullname"
            }
            if (phone.isEmpty()){
                Phone.error = "Please Enter Phone No"
            }
            else {
                register(userEmail, name, phone, password);
            }
        }

    }

    private fun register(email: String, name: String, phone: String, password: String) {
        db.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = db.push().key
                    val userData = UserData(id = id, fullName =  name, email= email, phoneNo =  phone,password= password)
                    db.child(id!!).setValue(userData)
                    Toast.makeText(this@CreateAccount, "Register Ok", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@CreateAccount, LoginUser::class.java))
                    finish()
                } else {
                    Toast.makeText(this@CreateAccount, "Register not Ok", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(dberror: DatabaseError) {
                Toast.makeText(this@CreateAccount, "DB error: ${dberror}", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }


    private fun checking(): Boolean {

        if (Name.text.toString().trim { it <= ' ' }.isNotEmpty()
            && Phone.text.toString().trim { it <= ' ' }.isNotEmpty()
            && EmailRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
            && PasswordRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}
