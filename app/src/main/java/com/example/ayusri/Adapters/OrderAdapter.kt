package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Orders
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.*

class OrderAdapter(private var DisList: ArrayList<Orders>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_seller_orders_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DisList[position]
        holder.orderId.text = "Order Id : "+currentDoc.orderId
        holder.tvtop.text = currentDoc.prodName
        holder.tvdis.text = currentDoc.prodDesc
        holder.tvPrice.text = currentDoc.prodPrice

        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef.orderByChild("id").equalTo(currentDoc.customerId.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
//                    if(dataSnapshot.children == null)
                        for (userSnapshot in snapshot.children){
                            val userdata = userSnapshot.getValue(UserData::class.java)
                            if (userdata != null) {
                                holder.tvCustomerName.text= userdata.fullName.toString()
                                holder.tvCustomerAdd.text = userdata.address.toString()
                                holder.tvCustomerTel.text = userdata.phoneNo.toString()
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        holder.tvAmount.text = currentDoc.amountOrdered


    }

    override fun getItemCount(): Int {
        return DisList.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvtop: TextView = itemView.findViewById(R.id.productName)
        val tvdis: TextView = itemView.findViewById(R.id.productDescription)
        val tvPrice: TextView = itemView.findViewById(R.id.productPrice)

        val tvAmount : TextView = itemView.findViewById(R.id.orderAmount)

        val tvCustomerName : TextView = itemView.findViewById(R.id.customerName)
        val tvCustomerAdd : TextView = itemView.findViewById(R.id.customerAdd)
        val tvCustomerTel : TextView = itemView.findViewById(R.id.customerTel)


        val orderId:TextView = itemView.findViewById(R.id.orderId)
//
//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }
}