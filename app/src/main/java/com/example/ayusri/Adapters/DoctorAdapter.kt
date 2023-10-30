package com.example.ayusri.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Functions.Doctors.DoctorAdminUpdate
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class DoctorAdapter(private var DocList: ArrayList<UserData>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

//    private lateinit var mListener: onItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//
//    }
//    fun setOnItemClickListener(clickListener: onItemClickListener){
//        mListener = clickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_doctor_list_admin_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DocList[position]
        holder.tvDoc.text = currentDoc.fullName
        holder.tvPhone.text = currentDoc.phoneNo
        holder.tvEmail.text = currentDoc.email
        holder.tvHos.text = currentDoc.address
        holder.tvSpec.text = currentDoc.specialization

        Picasso.get().load(Uri.parse(currentDoc.imageUri)).into(holder.imgDocImage)


        holder.btnDeleteDocRecord.setOnClickListener {
//            val deletedData = DocList[position]
//            FirebaseDatabase.getInstance().getReference("Doctors").child(deletedData.doctorId.toString()).removeValue()
            val context = holder.itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirm delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete") { _, _ ->
                    val deletedData = DocList[position]
                    FirebaseDatabase.getInstance().getReference("users")
                        .child(deletedData.id.toString()).removeValue()
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()
            alertDialog.show()
        }


        holder.btnUpdateDocRecord.setOnClickListener {


            var intent = Intent(holder.btnUpdateDocRecord.context, DoctorAdminUpdate::class.java)
            intent.putExtra("doctorId",currentDoc.id)
            intent.putExtra("doctorName",currentDoc.fullName)
            intent.putExtra("doctorPhone",currentDoc.phoneNo)
            intent.putExtra("doctorEmail",currentDoc.email)
            intent.putExtra("doctorAdd",currentDoc.address)
            intent.putExtra("doctorSpec",currentDoc.specialization)
            intent.putExtra("doctorPass",currentDoc.password)
            intent.putExtra("doctorImg",currentDoc.imageUri)


            holder.btnUpdateDocRecord.context.startActivity(intent)


        }

    }

    override fun getItemCount(): Int {
        return DocList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDoc: TextView = itemView.findViewById(R.id.docname)
        val tvPhone: TextView = itemView.findViewById(R.id.docphone)
        val tvEmail: TextView = itemView.findViewById(R.id.docemail)
        val tvHos: TextView = itemView.findViewById(R.id.dochospital)
        val tvSpec: TextView = itemView.findViewById(R.id.docSpec)
        val btnDeleteDocRecord: Button = itemView.findViewById(R.id.btnDeleteDocRecord)
        val btnUpdateDocRecord: Button = itemView.findViewById(R.id.btnUpdateDocRecord)
        val imgDocImage:ImageView = itemView.findViewById(R.id.imgDocImage)

//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }

    private fun updateDocData(
        docId: String?,
        docName: String?,
        docTel: String?,
        docEmail: String?,
        docAdd: String?,
        docSpec: String?,
        docPss: String?,
        img: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("users").child(docId.toString())
        val docInfo = UserData(
            id = docId, fullName = docName, phoneNo = docTel, email = docEmail, address = docAdd,
            userType = "Doctor", specialization = docSpec, password = docPss, imageUri = img
        )
        if (dbRef != null) {
            dbRef.setValue(docInfo)
        } else {
            println("bdvvvvvvvvvvvvv")
        }
    }

}