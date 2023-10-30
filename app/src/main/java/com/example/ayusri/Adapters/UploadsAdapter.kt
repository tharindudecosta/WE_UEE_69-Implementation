package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
//import com.example.imageuploader.R
import com.squareup.picasso.Picasso

class UploadsAdapter(private var mList: List<String>, private var titleList: List<String>) :
    RecyclerView.Adapter<UploadsAdapter.ImagesViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvUploadTitle: TextView = itemView.findViewById(R.id.tvUploadTitle)
        val btnDeleteDocRecord: Button = itemView.findViewById(R.id.btnDeleteDocRecord)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_customer_uploads_item, parent, false)
        return ImagesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val imageUrl = mList[position]

        val title = titleList[position]

        Picasso.get().load(imageUrl).into(holder.imageView)
        holder.tvUploadTitle.text = title

        holder.btnDeleteDocRecord.setOnClickListener {
            val context = holder.itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirm delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete") { _, _ ->
                    val deletedData = titleList[position]

                    val documentReference =
                        db.collection("images").document(deletedData.toString())

                    documentReference.delete()
                        .addOnSuccessListener {
                            // Document was successfully deleted
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            // An error occurred while deleting the document
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("Cancel", null)
                .create()
            alertDialog.show()
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }


}
