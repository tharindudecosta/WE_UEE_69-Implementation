package com.example.ayusri.Functions.Customers


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DisAdapter
import com.example.ayusri.Adapters.ProductAdapter
import com.example.ayusri.Adapters.ProductAdapterCustomer
import com.example.ayusri.Models.Products
import com.example.ayusri.R
import com.google.firebase.database.*

class CustomerProductsFetch : AppCompatActivity() {
    private lateinit var DisRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var medilist: ArrayList<Products>
    private lateinit var adapter: DisAdapter

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_fetch_customer)

        DisRecyclerView = findViewById(R.id.DisRecyclerView)
        DisRecyclerView.layoutManager = LinearLayoutManager(this)
        DisRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        medilist = arrayListOf<Products>()

        getDisease()
    }
    private fun getDisease() {
        DisRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Products")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medilist.clear()
                if (snapshot.exists()) {
                    for (disSnap in snapshot.children) {
                        val disData = disSnap.getValue(Products::class.java)
                        medilist.add(disData!!)
                    }
                    val mAdapter = ProductAdapterCustomer(medilist)
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


}