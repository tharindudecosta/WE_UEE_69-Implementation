package com.example.ayusri.Functions.Customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DoctorAdapter
import com.example.ayusri.Adapters.DoctorAdapterCustomer
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.*

class CustomerDoctorsFetch : AppCompatActivity() {
    private lateinit var DocRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var docList: ArrayList<UserData>
    private lateinit var adapter: DoctorAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_customer)

        DocRecyclerView = findViewById(R.id.DocRecyclerView)
        DocRecyclerView.layoutManager = LinearLayoutManager(this)
        DocRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        docList = arrayListOf<UserData>()

        getDoctors()
    }

    private fun getDoctors() {
        DocRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val query = dbRef.orderByChild("userType").equalTo("Doctor")

        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                docList.clear()
                if (snapshot.exists()) {
                    for (docSnap in snapshot.children) {
                        val docData = docSnap.getValue(UserData::class.java)
                        docList.add(docData!!)
                    }
                    val mAdapter = DoctorAdapterCustomer(docList)
                    DocRecyclerView.adapter = mAdapter
                    DocRecyclerView.visibility =View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}