package com.example.ayusri.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Functions.Common.CreateAccount
import com.example.ayusri.Functions.Customers.ScheduleAppointments
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class DoctorAdapterCustomer(private var DocList: ArrayList<UserData>) :
    RecyclerView.Adapter<DoctorAdapterCustomer.ViewHolder>() {

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
            .inflate(R.layout.activity_doctor_list_customer_item, parent, false)
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

        holder.btnScheduleAppointment.setOnClickListener {
            var intent = Intent(holder.btnScheduleAppointment.context, ScheduleAppointments::class.java)
            intent.putExtra("doctorId",currentDoc.id)
            intent.putExtra("doctorName",currentDoc.fullName)
            intent.putExtra("doctorPhone",currentDoc.phoneNo)
            intent.putExtra("doctorEmail",currentDoc.email)
            intent.putExtra("doctorAdd",currentDoc.address)
            holder.btnScheduleAppointment.context.startActivity(intent)
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
        val btnScheduleAppointment: Button = itemView.findViewById(R.id.btnScheduleAppointment)
        val imgDocImage:ImageView = itemView.findViewById(R.id.imgDocImage)


//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }


}