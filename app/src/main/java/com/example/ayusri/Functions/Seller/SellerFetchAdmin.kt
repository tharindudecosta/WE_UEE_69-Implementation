package com.example.ayusri.Functions.Seller

import com.example.ayusri.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DoctorAdapter
import com.example.ayusri.Adapters.SellerAdapter
import com.example.ayusri.Models.UserData
import com.google.firebase.database.*

class SellerFetchAdmin : AppCompatActivity() {
    private lateinit var sellRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var sellList: ArrayList<UserData>
    private lateinit var adapter: DoctorAdapter
    private lateinit var btnNewSell: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_list_fetch_admin)

        sellRecyclerView = findViewById(R.id.sellRecyclerView)
        sellRecyclerView.layoutManager = LinearLayoutManager(this)
        sellRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        btnNewSell = findViewById(R.id.btnNewSell)
        sellList = arrayListOf<UserData>()

        btnNewSell.setOnClickListener {
            val intent = Intent(this, SellerAdd::class.java)
            startActivity(intent)
        }
        getSellers()
    }

    private fun getSellers() {
        sellRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val query = dbRef.orderByChild("userType").equalTo("Seller")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sellList.clear()
                if (snapshot.exists()) {
                    for (docSnap in snapshot.children) {
                        val docData = docSnap.getValue(UserData::class.java)
                        sellList.add(docData!!)
                    }
                    val mAdapter = SellerAdapter(sellList)
                    sellRecyclerView.adapter = mAdapter

//                    mAdapter.setOnItemClickListener(object: DocAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//                            val intent = Intent(this@DoctorFetchCustomer,test1::class.java)
//                            intent.putExtra("docID", docList[position].id)
//                            intent.putExtra("docName", docList[position].fullName)
//                            intent.putExtra("docEmail", docList[position].email)
//                            intent.putExtra("docPhone", docList[position].phoneNo)
//                            intent.putExtra("docHospital", docList[position].address)
//                            intent.putExtra("docSpecialization", docList[position].specialization)
//                            startActivity(intent)
//                        }
//
//                    })
                    sellRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
                else{
                    sellRecyclerView.visibility = View.GONE
                    tvLoadingData.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}