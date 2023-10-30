package com.example.ayusri.Functions.Customers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ayusri.Models.Orders
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CustomerOrderItem : AppCompatActivity() {

    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDesc: TextView

    private lateinit var tvSellerName: TextView
    private lateinit var tvShopName: TextView
    private lateinit var tvSellerTel: TextView

    private lateinit var amount: EditText

    private lateinit var btnOrder: Button

    val global = UserGlobal.getInstance()
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order_item)

        tvProductName = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvPrice)
        tvProductDesc = findViewById(R.id.tvDesc)

        tvSellerName = findViewById(R.id.tvSellerName)
        tvShopName = findViewById(R.id.tvShopName)
        tvSellerTel = findViewById(R.id.tvSellerTel)

        amount = findViewById(R.id.etAmount)

        btnOrder = findViewById(R.id.btnOrder)

        dbRef = FirebaseDatabase.getInstance().getReference("orders")

        var prodId = intent.getStringExtra("productId")
        var prodName = intent.getStringExtra("productTopic")
        var prodPrice = intent.getStringExtra("productPrice")
        var prodDesc = intent.getStringExtra("productDesc")
        var sellerName = intent.getStringExtra("sellerName")
        var sellerShop = intent.getStringExtra("sellerShop")
        var sellerTel = intent.getStringExtra("sellerTel")

        tvProductName.text = prodName.toString()
        tvProductPrice.text = prodPrice.toString()
        tvProductDesc.text = prodDesc.toString()

        tvSellerName.text = sellerName.toString()
        tvShopName.text = sellerShop.toString()
        tvSellerTel.text = sellerTel.toString()

        btnOrder.setOnClickListener {
            makeOrder(prodId, prodName, prodPrice, prodDesc, sellerName, sellerShop, sellerTel)
        }

    }

    private fun makeOrder(
        prodId: String?,
        prodName: String?,
        prodPrice: String?,
        prodDesc: String?,
        sellerName: String?,
        sellerShop: String?,
        sellerTel: String?
    ) {
        val amountOrdered = amount.text.toString()

        if (amountOrdered.isEmpty()) {
            amount.error = "Please enter Amount"
        }else{
            btnOrder.isEnabled = false
            val orderId = dbRef.push().key!!

            val OrderNew = Orders(orderId=orderId, prodId=prodId, prodName=prodName, prodPrice=prodPrice, prodDesc=prodDesc,
                sellerName=sellerName, sellerShop=sellerShop, sellerTel=sellerTel, customerId = global.id, amountOrdered = amountOrdered)

            dbRef.child(orderId).setValue(OrderNew)
                .addOnCompleteListener {
                    Toast.makeText(this, "Order request sent successfully", Toast.LENGTH_LONG).show()
                    btnOrder.isEnabled = true
                    amount.text.clear()

                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Order Completed")
                        .setMessage("Order request sent successfully !!!")
                        .setNegativeButton("OK", null)
                        .create()
                    alertDialog.show()
                }
                .addOnFailureListener { err ->
                    btnOrder.isEnabled = true
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

        }


    }
}