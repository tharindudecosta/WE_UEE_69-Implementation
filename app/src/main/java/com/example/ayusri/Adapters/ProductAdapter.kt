package com.example.ayusri.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Functions.Doctors.DoctorAdminUpdate
import com.example.ayusri.Functions.Seller.SellerProductUpdate
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class ProductAdapter(private var DisList: ArrayList<Products>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_product_list_item_seller, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DisList[position]
        holder.tvtop.text = currentDoc.productTopic
        holder.tvdis.text = currentDoc.productDesc
        holder.tvPrice.text = currentDoc.productPrice

        Picasso.get().load(Uri.parse(currentDoc.imageUri)).into(holder.imgProdImage)

        holder.btnDelete.setOnClickListener {
//            val deletedData = DocList[position]
//            FirebaseDatabase.getInstance().getReference("Doctors").child(deletedData.doctorId.toString()).removeValue()
            val context = holder.itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirm delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete") { _, _ ->
                    val deletedData = DisList[position]
                    FirebaseDatabase.getInstance().getReference("Products")
                        .child(deletedData.productID.toString()).removeValue()
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()
            alertDialog.show()
        }


        holder.btnUpdate.setOnClickListener {

            var intent = Intent(holder.btnUpdate.context, SellerProductUpdate::class.java)
            intent.putExtra("productId",currentDoc.productID)
            intent.putExtra("productName",currentDoc.productTopic)
            intent.putExtra("productDesc",currentDoc.productDesc)
            intent.putExtra("productPrice",currentDoc.productPrice)
            intent.putExtra("productImg",currentDoc.imageUri)


            holder.btnUpdate.context.startActivity(intent)


//            println(currentDoc.productID.toString())
//            val builder = AlertDialog.Builder(holder.itemView.context)
//            builder.setTitle("Update Item")
//
//            val editTextProdName = EditText(holder.itemView.context)
//            val editTextProdPrice = EditText(holder.itemView.context)
//            val editTextProdDesc = EditText(holder.itemView.context)
//
//            val prodId = currentDoc.productID.toString()
//            val img = currentDoc.imageUri.toString()
//
//            editTextProdName.setText(currentDoc.productTopic)
//            editTextProdDesc.setText(currentDoc.productDesc)
//            editTextProdPrice.setText(currentDoc.productPrice)
//
//            builder.setView(
//                LinearLayout(holder.itemView.context).apply {
//                    orientation = LinearLayout.VERTICAL
//                    addView(editTextProdName)
//                    addView(editTextProdDesc)
//                    addView(editTextProdPrice)
//                }
//            )
//            builder.setPositiveButton("Update") { _, _ ->
//                val prodName = editTextProdName.text.toString()
//                val prodDesc = editTextProdDesc.text.toString()
//                val prodPrice = editTextProdPrice.text.toString()
//                updateProdData(
//                    prodId, prodName, prodDesc, prodPrice,img
//                )
//            }
//            builder.setNegativeButton("Cancel", null)
//            builder.show()
        }
    }

    private fun updateProdData(
        prodId: String,
        prodName: String,
        prodDesc: String,
        prodPrice: String,
        img: String
    ) {
        val global = UserGlobal.getInstance()
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(prodId.toString())
        val prodInfo = Products(
            productID = prodId, productTopic = prodName, productDesc = prodDesc,
            productPrice = prodPrice, sellerId = global.id, imageUri = img
        )
        if (dbRef != null) {
            dbRef.setValue(prodInfo)
        } else {
            println("bdbcbcbcbcvbcvbfvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
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
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteDocRecord)
        val btnUpdate: Button = itemView.findViewById(R.id.btnUpdateDocRecord)
        val imgProdImage:ImageView = itemView.findViewById(R.id.imgProdImage)
//
//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }
}

private fun RequestCreator.into(imgProdImage: Button) {
    TODO("Not yet implemented")
}
