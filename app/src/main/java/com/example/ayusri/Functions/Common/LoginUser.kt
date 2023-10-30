package com.example.ayusri.Functions.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.example.ayusri.Functions.Admin.AdminLoginMain
import com.example.ayusri.Functions.Customers.CustomerLoginMain
import com.example.ayusri.Functions.Customers.CustomerProductsFetch
import com.example.ayusri.Functions.Doctors.DocLoginHomeActivity
import com.example.ayusri.Models.UserData
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.example.ayusri.Functions.Seller.SellerLoginHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginUser : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var db: DatabaseReference

    lateinit var signin: Button
    lateinit var Email: EditText
    lateinit var Password: EditText
    lateinit var Register: Button
    lateinit var adminLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseDatabase = FirebaseDatabase.getInstance()
        db = firebaseDatabase.reference.child("users")


        signin = findViewById(R.id.sigin)
        Email = findViewById(R.id.lemail)
        Password = findViewById(R.id.lpassword)
        Register = findViewById(R.id.create)
//        adminLogin = findViewById(R.id.adminLogin)

//        adminLogin.setOnClickListener {
//            var intent =Intent(this, LoginAdmin::class.java)
//            startActivity(intent)
//        }

        Register.setOnClickListener {
            var intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
            finish()
        }


        //Login
        signin.setOnClickListener {

            signin.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.roundstyle_clicked)

            val userEmail = Email.text.toString()
            val userPassword = Password.text.toString()

            if (userEmail.isEmpty()) {
                Email.error = "Please enter Email"
            }
//            if (android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
//                Email.error = "Please enter valid email address"
//            }
            if (userPassword.isEmpty()) {
                Password.error = "Please enter Password"
            } else {
                loginUser(userEmail, userPassword)
            }
            object : CountDownTimer(1400, 50) {
                override fun onTick(arg0: Long) {
                    // TODO Auto-generated method stub
                }

                override fun onFinish() {
                    signin.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.roundstyle)
                }
            }.start()
        }

    }

    private fun loginUser(email: String, password: String) {
        db.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
//                    if(dataSnapshot.children == null)
                    for (userSnapshot in dataSnapshot.children) {
                        val userdata = userSnapshot.getValue(UserData::class.java)

                        if (userdata != null && userdata.password == password) {
                            Toast.makeText(this@LoginUser, "Login Success", Toast.LENGTH_SHORT).show()

                            val global = UserGlobal.getInstance()

                            global.id = userdata.id
                            global.fullName = userdata.fullName
                            global.email = userdata.email
                            global.phoneNo = userdata.phoneNo
                            global.password = userdata.password
                            global.userType = userdata.userType
                            global.address = userdata.address
                            global.imageUri = userdata.imageUri

                            if (userdata.userType.equals("Customer")) {
                                startActivity(Intent(this@LoginUser, CustomerLoginMain::class.java))
                                finish()
                            }
                            if (userdata.userType.equals("Doctor")) {

                                global.specialization = userdata.specialization

                                startActivity(
                                    Intent(
                                        this@LoginUser,
                                        DocLoginHomeActivity::class.java
                                    )
                                )
                                finish()
                            }
                            if (userdata.userType.equals("Seller")) {

                                global.storeName = userdata.storeName

                                startActivity(Intent(this@LoginUser, SellerLoginHome::class.java))
                                finish()
                            }
                            if (userdata.userType.equals("Admin")) {
                                startActivity(Intent(this@LoginUser, AdminLoginMain::class.java))
                                finish()
                            }

                        } else {
                            Password.error = "Please enter Correct Password"
                            Toast.makeText(this@LoginUser, "Login unsuccessful", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            override fun onCancelled(dberror: DatabaseError) {
                Toast.makeText(this@LoginUser, "DB error: ${dberror}", Toast.LENGTH_SHORT).show()

            }
        })

    }


    private fun checking(): Boolean {
        if (Email.text.toString().trim { it <= ' ' }.isNotEmpty()
            && Password.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }

}
