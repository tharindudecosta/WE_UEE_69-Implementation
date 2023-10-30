package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Appointments
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.*

class AppointmentAdapterDoctor(private var AppointmentList: ArrayList<Appointments>) :
    RecyclerView.Adapter<AppointmentAdapterDoctor.ViewHolder>() {

    private lateinit var dbRef: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_doctor_appointments_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = AppointmentList[position]
//        holder.tvDoc.text = currentDoc.docName
//        holder.tvPhone.text = currentDoc.docPhone
//        holder.tvEmail.text = currentDoc.docEmail
//        holder.tvHos.text = currentDoc.docAddress
        holder.tvAppId.text = "Appointment Id : "+currentDoc.appointmentId

        holder.tvPtName.text = currentDoc.cusName
        holder.tvAilment.text = currentDoc.ailment
        holder.tvDate.text = currentDoc.date
        holder.tvTime.text = currentDoc.time

        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef.orderByChild("id").equalTo(currentDoc.cusId.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
//                    if(dataSnapshot.children == null)
                        for (userSnapshot in snapshot.children){
                            val userdata = userSnapshot.getValue(UserData::class.java)
                            if (userdata != null) {

                                holder.patientPhone.text= userdata.phoneNo.toString()

                            }
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

    override fun getItemCount(): Int {
        return AppointmentList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvDoc: TextView = itemView.findViewById(R.id.docname)
//        val tvPhone: TextView = itemView.findViewById(R.id.docphone)
//        val tvEmail: TextView = itemView.findViewById(R.id.docemail)
//        val tvHos: TextView = itemView.findViewById(R.id.dochospital)

        val tvPtName: TextView = itemView.findViewById(R.id.patientName)
        val tvAilment: TextView = itemView.findViewById(R.id.patientAilment)
        val tvDate: TextView = itemView.findViewById(R.id.appDate)
        val tvTime: TextView = itemView.findViewById(R.id.appTime)
        val patientPhone :TextView = itemView.findViewById(R.id.patientPhone)
        val tvAppId: TextView = itemView.findViewById(R.id.tvAppId)



//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }


}