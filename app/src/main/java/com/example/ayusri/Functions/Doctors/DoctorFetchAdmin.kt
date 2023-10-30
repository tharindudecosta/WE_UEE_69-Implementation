package com.example.ayusri.Functions.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.DoctorAdapter
import com.example.ayusri.Models.UserData
import com.example.ayusri.R

import com.google.firebase.database.*

class DoctorFetchAdmin : AppCompatActivity() {
    private lateinit var DocRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var docList: ArrayList<UserData>
    private lateinit var adapter: DoctorAdapter
    private lateinit var btnNewDoc: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_admin)
//        setContentView(R.layout.activity_doc_fetching)

        DocRecyclerView = findViewById(R.id.DocRecyclerView)
        DocRecyclerView.layoutManager = LinearLayoutManager(this)
        DocRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        btnNewDoc = findViewById(R.id.btnNewDoc)
        docList = arrayListOf<UserData>()

        btnNewDoc.setOnClickListener {
            val intent = Intent(this, DoctorAdd::class.java)
            startActivity(intent)
        }

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
                    val mAdapter = DoctorAdapter(docList)
                    DocRecyclerView.adapter = mAdapter

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
                    DocRecyclerView.visibility =View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
                else{
                    DocRecyclerView.visibility = View.GONE
                    tvLoadingData.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}