package com.example.ayusri.location

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.ayusri.Adapters.LocAdapter

import com.example.ayusri.Models.Location
import com.example.ayusri.R
import com.example.ayusri.loc_details
import com.google.firebase.database.*

class locfetching : AppCompatActivity() {

    private lateinit var LocRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var loclist: ArrayList<Location>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locfetching2)


        var addisbtn=findViewById<Button>(R.id.addlocsub)
        addisbtn.setOnClickListener{
            openInsertDialog()
        }
        LocRecyclerView = findViewById(R.id.LocRecyclerView)
        LocRecyclerView.layoutManager = LinearLayoutManager(this)
        LocRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        loclist = arrayListOf<Location>()

        getDisease()

    }

    private fun getDisease() {

        LocRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Locations")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loclist.clear()
                if (snapshot.exists()) {
                    for (locSnap in snapshot.children) {
                        val locData = locSnap.getValue(Location::class.java)
                        loclist.add(locData!!)
                    }
                    val mAdapter = LocAdapter(loclist)
                    LocRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: LocAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@locfetching,loc_details::class.java)

                            intent.putExtra("locID", loclist[position].locID)
                            intent.putExtra("locName", loclist[position].locName)
                            intent.putExtra("locLoc", loclist[position].locLoc)
                            intent.putExtra("locPhone", loclist[position].locPhone)
                            startActivity(intent)
                        }

                    })
                    LocRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }




    private fun openInsertDialog() {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.xactivity_add_location,null)
        mDialog.setView(mDialogView)

        val disTopic = mDialogView.findViewById<EditText>(R.id.locationname)
        val disAdd = mDialogView.findViewById<EditText>(R.id.locationloc)
        val disphone =mDialogView.findViewById<EditText>(R.id.locationphone)
        //  val disAddnew = mDialogView.findViewById<EditText>(R.id.disAdd2)

        val btnAdd = mDialogView.findViewById<Button>(R.id.locationaddbutton)

        val alertDialog = mDialog.create()
        alertDialog.show()
        dbRef = FirebaseDatabase.getInstance().getReference("Locations")
        btnAdd.setOnClickListener {
            val empName = disTopic.text.toString()
            val empAge = disAdd.text.toString()
            val emphone = disphone.text.toString()
            // val empSalary = disAddnew.text.toString()

            if (empName.isEmpty()) {
                disTopic.error = "Please enter Topic"
            }
            if (empAge.isEmpty()) {
                disAdd.error = "Please enter Discription"
            }
            if (emphone.isEmpty()) {
                disphone.error = "Please enter salary"
            }
            else {
                val locID = dbRef.push().key!!

                val employee = Location(locID, empName, empAge, emphone)

                dbRef.child(locID).setValue(employee)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                        disTopic.text.clear()
                        disAdd.text.clear()
                        disphone.text.clear()
                        //  disAddnew.text.clear()


                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
                alertDialog.dismiss()

            }
        }

    }


}
