package com.example.ayusri.Functions.Seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DisAdapter
import com.example.ayusri.Adapters.OrderAdapter
import com.example.ayusri.Adapters.OrderAdapterCustomer
import com.example.ayusri.Models.Orders
import com.example.ayusri.R
import com.google.firebase.database.*

class SellerOrdersList : AppCompatActivity() {

    private lateinit var DisRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var ordList: ArrayList<Orders>
    private lateinit var adapter: DisAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_orders)

        DisRecyclerView = findViewById(R.id.ordersRecyclerView)
        DisRecyclerView.layoutManager = LinearLayoutManager(this)
        DisRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        ordList = arrayListOf<Orders>()

        getOrders()
    }

    private fun getOrders() {
        DisRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("orders")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordList.clear()
                if (snapshot.exists()) {
                    for (disSnap in snapshot.children) {
                        val disData = disSnap.getValue(Orders::class.java)
                        ordList.add(disData!!)
                    }
                    val mAdapter = OrderAdapter(ordList)
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