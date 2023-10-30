package com.example.ayusri

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
import com.example.ayusri.Adapters.DisAdapter
import com.example.ayusri.Models.Disease
import com.google.firebase.database.*

class DiseaseFetching : AppCompatActivity() {

    private lateinit var DisRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var dislist: ArrayList<Disease>
    private lateinit var adapter: DisAdapter

    private lateinit var dbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_disease_fetching)

        var addisbtn=findViewById<Button>(R.id.addissub)
        addisbtn.setOnClickListener{
            openInsertDialog()
        }
        DisRecyclerView = findViewById(R.id.DisRecyclerView)
        DisRecyclerView.layoutManager = LinearLayoutManager(this)
        DisRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        dislist = arrayListOf<Disease>()

        getDisease()


    }

    private fun getDisease() {
        DisRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Diseases")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dislist.clear()
                if (snapshot.exists()) {
                    for (disSnap in snapshot.children) {
                        val disData = disSnap.getValue(Disease::class.java)
                        dislist.add(disData!!)
                    }
                    val mAdapter = DisAdapter(dislist)
                    DisRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object:DisAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@DiseaseFetching,DisDetailsActivity::class.java)
                            //put extra
                            intent.putExtra("disID", dislist[position].disID)
                            intent.putExtra("disTopic", dislist[position].disTopic)
                            intent.putExtra("disAdd", dislist[position].disAdd)
                            startActivity(intent)
                        }

                    })
                    DisRecyclerView.visibility = View.VISIBLE
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
        val mDialogView = inflater.inflate(R.layout.xactivity_add_disease, null)
        mDialog.setView(mDialogView)

        val disTopic = mDialogView.findViewById<EditText>(R.id.topicdisease)
        val disAdd = mDialogView.findViewById<EditText>(R.id.disesaseabout)
      //  val disAddnew = mDialogView.findViewById<EditText>(R.id.disAdd2)

        val btnAdd = mDialogView.findViewById<Button>(R.id.diseaseaddbutton)

        val alertDialog = mDialog.create()
        alertDialog.show()
        dbRef = FirebaseDatabase.getInstance().getReference("Diseases")
        btnAdd.setOnClickListener {
            val empName = disTopic.text.toString()
            val empAge = disAdd.text.toString()
            // val empSalary = disAddnew.text.toString()

            if (empName.isEmpty()) {
                disTopic.error = "Please enter Topic"
            }
            if (empAge.isEmpty()) {
                disAdd.error = "Please enter Discription"
            }
//            if (empSalary.isEmpty()) {
//                disAddnew.error = "Please enter salary"
//            }
            else {
                val disId = dbRef.push().key!!

                val employee = Disease(disId, empName, empAge)

                dbRef.child(disId).setValue(employee)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                        disTopic.text.clear()
                        disAdd.text.clear()
                        //  disAddnew.text.clear()


                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
                alertDialog.dismiss()

            }
        }

        }


    }






