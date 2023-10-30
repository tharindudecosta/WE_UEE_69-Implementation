package com.example.ayusri.calculate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ayusri.Functions.Customers.CustomerLoginMain
//import com.example.ayusri.databinding.ActivityClientSetOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ClientSetOrders : AppCompatActivity() {
//    private lateinit var binding: ActivityClientSetOrdersBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityClientSetOrdersBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

//        binding.ordersave.setOnClickListener {
//
//            val userId = firebaseAuth.currentUser?.uid
//
//            if (userId != null) {
//
//                val projectname = binding.pojectname.text.toString()
//                val timePeriod = binding.timePeriod.text.toString().toIntOrNull()
//
//                if (timePeriod != null && timePeriod <= 10) {
//                    val result = calculatePrice(timePeriod.toString())
//                    binding.orderPrice.text = "Rs.%.2f".format(result)
//                } else {
//                    Toast.makeText(this, "Please enter a valid time period (1-10)", Toast.LENGTH_SHORT).show()
//                }
//
//                val goals = binding.goals.text.toString()
//                val descrption = binding.description.text.toString()
//                val OrderEmail = binding.OrderEmail.text.toString()
//
//                createOrder(userId, projectname, timePeriod, goals, descrption, OrderEmail)
//
//            } else {
//                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
//            }
//        }


//        binding.calPrice.setOnClickListener {
//            val timePeriod = binding.timePeriod.text.toString()
//            val result = calculatePrice(timePeriod)
//
//            binding.orderPrice.text = "Rs.$result"
//        }
    }

//    private fun calculatePrice(timePeriod: String): Double {
//        val timePeriodInt = timePeriod.toIntOrNull() ?: 0
//
//        if (timePeriodInt in 1..100) {
//            val result = timePeriodInt * 100
//            var finalResult = result.toDouble() // Convert Int to Double
//
//            if (timePeriodInt >= 5) {
//                val discount = result * 0.05
//                finalResult -= discount
//            }
//            if (timePeriodInt >= 10) {
//                val discount = result * 0.08
//                finalResult -= discount
//            }
//            if (timePeriodInt >= 50) {
//                val discount = result * 0.15
//                finalResult -= discount
//            }
//
//            binding.orderPrice.text = "Rs.%.2f".format(finalResult)
//            return finalResult
//        } else {
//            Toast.makeText(this, "Maximum Item Only 10", Toast.LENGTH_LONG).show()
//            return 0.0
//        }
//    }


    private fun createOrder(
        userId: String,
        projectname: String,
        timePeriod: Int?,
        goals: String,
        descrption: String,
        OrderEmail: String
    ) {
        val orderRef = FirebaseDatabase.getInstance().getReference("orders")

        val orderData = mapOf(
            "Pname" to projectname,
            "orderTP" to timePeriod,
            "goals" to goals,
            "description" to descrption,
            "Remail" to OrderEmail,
        )

        // Use push() to generate a unique key for each order
        orderRef.child(userId).push().setValue(orderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Order created successfully", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, CustomerLoginMain::class.java)
                startActivity(intent1)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error creating profile", Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener {
                // Dismiss any loading dialogs or indicators and handle the completion of the order creation process
            }
    }
}