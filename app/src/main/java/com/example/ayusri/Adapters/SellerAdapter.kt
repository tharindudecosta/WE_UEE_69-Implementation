package com.example.ayusri.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Functions.Seller.SellerAdminUpdate
import com.example.ayusri.Models.UserData
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class SellerAdapter (private var SellList:ArrayList<UserData>):
    RecyclerView.Adapter<SellerAdapter.ViewHolder>() {

//    private lateinit var mListener: onItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//
//    }
//    fun setOnItemClickListener(clickListener: onItemClickListener){
//        mListener = clickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_seller_list_item_admin,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = SellList[position]
        holder.tvSeller.text = currentDoc.fullName
        holder.tvPhone.text = currentDoc.phoneNo
        holder.tvEmail.text = currentDoc.email
        holder.tvAdd.text = currentDoc.address
        holder.tvShop.text = currentDoc.storeName
        Picasso.get().load(Uri.parse(currentDoc.imageUri)).into(holder.imgSellerImage)

        holder.btnDeleteDocRecord.setOnClickListener {
//            val deletedData = DocList[position]
//            FirebaseDatabase.getInstance().getReference("Doctors").child(deletedData.doctorId.toString()).removeValue()
            val context = holder.itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirm delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete") { _, _ ->
                    val deletedData = SellList[position]
                    FirebaseDatabase.getInstance().getReference("users").child(deletedData.id.toString()).removeValue()
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()
            alertDialog.show()
        }


        holder.btnUpdateDocRecord.setOnClickListener {

            var intent = Intent(holder.btnUpdateDocRecord.context, SellerAdminUpdate::class.java)
            intent.putExtra("sellerId",currentDoc.id)
            intent.putExtra("sellerName",currentDoc.fullName)
            intent.putExtra("sellerPhone",currentDoc.phoneNo)
            intent.putExtra("sellerEmail",currentDoc.email)
            intent.putExtra("sellerAdd",currentDoc.address)
            intent.putExtra("sellerShop",currentDoc.storeName)
            intent.putExtra("sellerPass",currentDoc.password)
            intent.putExtra("sellerImg",currentDoc.imageUri)

            holder.btnUpdateDocRecord.context.startActivity(intent)
//
//            println(currentDoc.id.toString())
//            val builder = AlertDialog.Builder(holder.itemView.context)
//            builder.setTitle("Update Item")
//
//            val editTextSellerName = EditText(holder.itemView.context)
//            val editTextSellerEmail = EditText(holder.itemView.context)
//            val editTextSellerTel = EditText(holder.itemView.context)
//            val editTextSellerAddress = EditText(holder.itemView.context)
//            val editTextSellerStore = EditText(holder.itemView.context)
//
//            val sellerId = currentDoc.id.toString()
//
//            editTextSellerName.setText(currentDoc.fullName)
//            editTextSellerTel.setText(currentDoc.phoneNo)
//            editTextSellerEmail.setText(currentDoc.email)
//            editTextSellerAddress.setText(currentDoc.address)
//            editTextSellerStore.setText(currentDoc.storeName)
//
//            builder.setView(
//                LinearLayout(holder.itemView.context).apply {
//                    orientation = LinearLayout.VERTICAL
//                    addView(editTextSellerName)
//                    addView(editTextSellerAddress)
//                    addView(editTextSellerTel)
//                    addView(editTextSellerEmail)
//                    addView(editTextSellerStore)
//                }
//            )
//            builder.setPositiveButton("Update") { _, _ ->
//                val sellerName = editTextSellerName.text.toString()
//                val sellerAdd = editTextSellerAddress.text.toString()
//                val sellerTel = editTextSellerTel.text.toString()
//                val sellerEmail = editTextSellerEmail.text.toString()
//                val sellerStore = editTextSellerStore.text.toString()
//                updateDocData(
//                    sellerId,sellerName,sellerTel,sellerEmail,sellerAdd,sellerStore
//                )
//            }
//            builder.setNegativeButton("Cancel", null)
//            builder.show()
        }

    }

    override fun getItemCount(): Int {
         return SellList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tvSeller:TextView = itemView.findViewById(R.id.sellName)
        val tvPhone:TextView = itemView.findViewById(R.id.sellPhone)
        val tvEmail:TextView = itemView.findViewById(R.id.sellEmail)
        val tvAdd:TextView = itemView.findViewById(R.id.sellAdd)
        val tvShop:TextView = itemView.findViewById(R.id.sellStore)
        val btnDeleteDocRecord : Button = itemView.findViewById(R.id.btnDeleteDocRecord)
        val btnUpdateDocRecord : Button = itemView.findViewById(R.id.btnUpdateDocRecord)
        val imgSellerImage:ImageView = itemView.findViewById(R.id.imgSellerImage)

//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }

    private fun updateDocData(
        sellerId:String?,
        sellerName:String?,
        sellerTel:String?,
        sellerEmail:String?,
        sellerAdd:String?,
        sellerStore:String?
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("users").child(sellerId.toString())
        val docInfo = UserData(
            id = sellerId, fullName = sellerName, phoneNo = sellerTel, email = sellerEmail,
            address = sellerAdd, userType = "Seller" , storeName = sellerStore, imageUri = null)
        if (dbRef != null) {
            dbRef.setValue(docInfo)
        }
        else{
            println("error")
        }
    }

}