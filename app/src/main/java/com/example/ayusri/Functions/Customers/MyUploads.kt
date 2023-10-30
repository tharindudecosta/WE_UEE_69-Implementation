package com.example.ayusri.Functions.Customers


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Adapters.UploadsAdapter
import com.example.ayusri.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SnapshotMetadata
import java.util.Objects

class MyUploads : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var btnUploadPage :Button
    private lateinit var adapter: UploadsAdapter
    private val mList = mutableListOf<String>()
    private val titleList = mutableListOf<String>()

    private val t = mutableListOf<SnapshotMetadata>()

    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_uploads)

        btnUploadPage = findViewById(R.id.btnUploadPage)
        btnUploadPage.setOnClickListener {
            val i = Intent(this, UploadImage::class.java)
            startActivity(i)
        }

        initVars()
        getImages()
    }

    private fun initVars() {
        recyclerView = findViewById(R.id.recyclerViewUploads)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UploadsAdapter(mList,titleList)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getImages() {
        progressBar.visibility = View.VISIBLE
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("images")
            .get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
//                    t.add(document)
                    val pic = document.getString("pic")
                    val titles = document.getString("uploadTitle")
                    pic?.let {
                        mList.add(it)
                    }
                    titles?.let {
                        titleList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
    }
}

