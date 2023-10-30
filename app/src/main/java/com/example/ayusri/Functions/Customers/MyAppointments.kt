package com.example.ayusri.Functions.Customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.AppointmentAdapterCustomer
import com.example.ayusri.Adapters.DoctorAdapter
import com.example.ayusri.Models.Appointments
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.*

class MyAppointments : AppCompatActivity() {

    private lateinit var DocRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var docList: ArrayList<Appointments>
    private lateinit var adapter: DoctorAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_appointments)

        DocRecyclerView = findViewById(R.id.DocRecyclerView)
        DocRecyclerView.layoutManager = LinearLayoutManager(this)
        DocRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        docList = arrayListOf<Appointments>()

        getAppointments()
    }

    private fun getAppointments() {
        DocRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        val global = UserGlobal.getInstance()


        dbRef = FirebaseDatabase.getInstance().getReference("appointments")

        val query = dbRef.orderByChild("cusId").equalTo(global.id)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                docList.clear()
                if (snapshot.exists()) {
                    for (docSnap in snapshot.children) {
                        val docData = docSnap.getValue(Appointments::class.java)
                        docList.add(docData!!)
                    }
                    val mAdapter = AppointmentAdapterCustomer(docList)
                    DocRecyclerView.adapter = mAdapter

//                    mAdapter.setOnItemClickListener(object: DocAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//                            val intent = Intent(this@CustomerDoctorsFetch,DocDetailsActivity::class.java)
//                            //put extra
//                            intent.putExtra("docID", docList[position].docID)
//                            intent.putExtra("docName", docList[position].docName)
//                            intent.putExtra("docEmail", docList[position].docEmail)
//                            intent.putExtra("docPhone", docList[position].docPhone)
//                            intent.putExtra("docHospital", docList[position].docHospital)
//                            startActivity(intent)
//                        }
//
//                    })
                    DocRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}