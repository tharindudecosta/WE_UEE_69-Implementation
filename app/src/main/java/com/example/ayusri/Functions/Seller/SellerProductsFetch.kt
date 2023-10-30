package com.example.ayusri.Functions.Seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DisAdapter
import com.example.ayusri.Adapters.ProductAdapter
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.*

class SellerProductsFetch : AppCompatActivity() {
    private lateinit var DisRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var medilist: ArrayList<Products>
    private lateinit var adapter: DisAdapter
    private lateinit var btnNewProd :Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_fetch_seller)

        DisRecyclerView = findViewById(R.id.DisRecyclerView)
        DisRecyclerView.layoutManager = LinearLayoutManager(this)
        DisRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        btnNewProd = findViewById(R.id.btnNewProd)

        medilist = arrayListOf<Products>()

        btnNewProd.setOnClickListener {
            val intent = Intent(this, SellerProductAdd::class.java)
            startActivity(intent)
        }

        getDisease()
    }
    private fun getDisease() {
        DisRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        val global = UserGlobal.getInstance()

        dbRef = FirebaseDatabase.getInstance().getReference("Products")

        val query = dbRef.orderByChild("sellerId").equalTo(global.id)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medilist.clear()
                if (snapshot.exists()) {
                    for (disSnap in snapshot.children) {
                        val disData = disSnap.getValue(Products::class.java)
                        medilist.add(disData!!)
                    }
                    val mAdapter = ProductAdapter(medilist)
                    DisRecyclerView.adapter = mAdapter

                    DisRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
//    private fun openInsertDialog() {
//        val mDialog = AlertDialog.Builder(this)
//        val inflater = layoutInflater
//        val mDialogView = inflater.inflate(R.layout.activity_product_add_seller, null)
//        mDialog.setView(mDialogView)
//
//        val disTopic = mDialogView.findViewById<EditText>(R.id.productName)
//        val disAdd = mDialogView.findViewById<EditText>(R.id.productDescription)
//        val disPrice = mDialogView.findViewById<EditText>(R.id.productPrice)
//
//        val btnAdd = mDialogView.findViewById<Button>(R.id.medicineaddbutton)
//
//        val alertDialog = mDialog.create()
//        alertDialog.show()
//        dbRef = FirebaseDatabase.getInstance().getReference("Products")
//        btnAdd.setOnClickListener {
//            val productName = disTopic.text.toString()
//            val productDescription = disAdd.text.toString()
//            val productPrice = disPrice.text.toString()
//
//
//            if (productName.isEmpty()) {
//                disTopic.error = "Please enter Topic"
//            }
//            if (productDescription.isEmpty()) {
//                disAdd.error = "Please enter Description"
//            }
//            if (productPrice.isEmpty()) {
//                disPrice.error = "Please enter Price"
//            }
//            else {
//                val Id = dbRef.push().key!!
//
//                val productsRecord = Products(Id,productName, productDescription, productPrice)
//
//                dbRef.child(Id).setValue(productsRecord)
//                    .addOnCompleteListener {
//                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
//
//                        disTopic.text.clear()
//                        disAdd.text.clear()
//                        disPrice.text.clear()
//
//                    }.addOnFailureListener { err ->
//                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                    }
//                alertDialog.dismiss()
//
//            }
//        }
//
//    }


}