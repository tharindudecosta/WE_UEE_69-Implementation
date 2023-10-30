package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Orders
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserData
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase

class OrderAdapterCustomer(private var DisList: ArrayList<Orders>) :
    RecyclerView.Adapter<OrderAdapterCustomer.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_customer_orders_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DisList[position]
        holder.orderId.text = "Order Id : "+currentDoc.orderId
        holder.tvtop.text = currentDoc.prodName
        holder.tvdis.text = currentDoc.prodDesc
        holder.tvPrice.text = currentDoc.prodPrice

        holder.tvproductSellerName.text = currentDoc.sellerName
        holder.tvproductSellerShop.text = currentDoc.sellerShop
        holder.tvproductSellerPhone.text = currentDoc.sellerTel

        holder.tvAmount.text = currentDoc.amountOrdered

        holder.tvbtnDeleteItem.setOnClickListener {
//            val deletedData = DocList[position]
//            FirebaseDatabase.getInstance().getReference("Doctors").child(deletedData.doctorId.toString()).removeValue()
            val context = holder.itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirm Cancel")
                .setMessage("Are you sure you want to cancel this?")
                .setPositiveButton("Delete") { _, _ ->
                    val deletedData = DisList[position]
                    FirebaseDatabase.getInstance().getReference("orders")
                        .child(deletedData.orderId.toString()).removeValue()
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()
            alertDialog.show()
        }


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
        val tvproductSellerName : TextView = itemView.findViewById(R.id.productSellerName)
        val tvproductSellerShop : TextView = itemView.findViewById(R.id.productSellerShop)
        val tvproductSellerPhone : TextView = itemView.findViewById(R.id.productSellerPhone)

        val tvbtnDeleteItem : Button = itemView.findViewById(R.id.btnDeleteItem)

        val orderId:TextView = itemView.findViewById(R.id.orderId)
//
//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }
}