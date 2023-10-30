package com.example.ayusri.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Functions.Admin.AdminLoginMain
import com.example.ayusri.Functions.Customers.CustomerLoginMain
import com.example.ayusri.Functions.Customers.CustomerOrderItem
import com.example.ayusri.Functions.Customers.ScheduleAppointments
import com.example.ayusri.Functions.Doctors.DocLoginHomeActivity
import com.example.ayusri.Functions.Seller.SellerLoginHome
import com.example.ayusri.Models.Products
import com.example.ayusri.Models.UserData
import com.example.ayusri.Models.UserGlobal
import com.example.ayusri.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ProductAdapterCustomer (private var DisList:ArrayList<Products>):
    RecyclerView.Adapter<ProductAdapterCustomer.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_list_item_customer, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DisList[position]
        holder.tvtop.text = currentDoc.productTopic
        holder.tvdis.text = currentDoc.productDesc
        holder.tvPrice.text = currentDoc.productPrice

        Picasso.get().load(Uri.parse(currentDoc.imageUri)).into(holder.imgProdImage)

        var intent = Intent(holder.btnOrderItem.context, CustomerOrderItem::class.java)

        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef.orderByChild("id").equalTo(currentDoc.sellerId.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
//                    if(dataSnapshot.children == null)
                        for (userSnapshot in snapshot.children){
                            val userdata = userSnapshot.getValue(UserData::class.java)
                            if (userdata != null) {
                                holder.tvSellerName.text= userdata.fullName.toString()
                                holder.tvSellerShop.text = userdata.storeName.toString()
                                holder.tvSellerTel.text = userdata.phoneNo.toString()
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        holder.btnOrderItem.setOnClickListener {
            intent.putExtra("productId",currentDoc.productID)
            intent.putExtra("productTopic",currentDoc.productTopic)
            intent.putExtra("productPrice",currentDoc.productPrice)
            intent.putExtra("productDesc",currentDoc.productDesc)
            intent.putExtra("sellerName",holder.tvSellerName.text.toString())
            intent.putExtra("sellerShop",holder.tvSellerShop.text.toString())
            intent.putExtra("sellerTel",holder.tvSellerTel.text.toString())
            holder.btnOrderItem.context.startActivity(intent)
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

        val tvSellerName : TextView = itemView.findViewById(R.id.productSellerName)
        val tvSellerShop : TextView = itemView.findViewById(R.id.productSellerShop)
        val tvSellerTel : TextView = itemView.findViewById(R.id.productSellerPhone)

        val btnOrderItem: Button = itemView.findViewById(R.id.btnOrderItem)

        val imgProdImage: ImageView = itemView.findViewById(R.id.imgProdImage)
//
//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }

    }
}