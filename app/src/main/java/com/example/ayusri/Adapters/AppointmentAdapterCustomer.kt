package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Appointments
import com.example.ayusri.R

class AppointmentAdapterCustomer(private var AppointmentList: ArrayList<Appointments>) :
    RecyclerView.Adapter<AppointmentAdapterCustomer.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_customer_appointments_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = AppointmentList[position]
        holder.tvAppId.text = "Appointment Id : "+currentDoc.appointmentId
        holder.tvDoc.text = currentDoc.docName
        holder.tvPhone.text = currentDoc.docPhone
        holder.tvEmail.text = currentDoc.docEmail
        holder.tvHos.text = currentDoc.docAddress

        holder.tvAilment.text = currentDoc.ailment
        holder.tvDate.text = currentDoc.date
        holder.tvTime.text = currentDoc.time

    }

    override fun getItemCount(): Int {
        return AppointmentList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDoc: TextView = itemView.findViewById(R.id.docname)
        val tvPhone: TextView = itemView.findViewById(R.id.docphone)
        val tvEmail: TextView = itemView.findViewById(R.id.docemail)
        val tvHos: TextView = itemView.findViewById(R.id.dochospital)

        val tvAilment: TextView = itemView.findViewById(R.id.patientAilment)
        val tvDate: TextView = itemView.findViewById(R.id.appDate)
        val tvTime: TextView = itemView.findViewById(R.id.appTime)

        val tvAppId: TextView = itemView.findViewById(R.id.tvAppId)



//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }


}