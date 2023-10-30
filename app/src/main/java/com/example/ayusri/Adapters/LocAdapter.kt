package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Location
import com.example.ayusri.R

class LocAdapter (private var locList:ArrayList<Location>):
    RecyclerView.Adapter<LocAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.xloclist, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = locList[position]
        holder.tvtop.text = currentDoc.locName
        holder.tvdis.text = currentDoc.locLoc
         holder.tvpres.text = currentDoc.locPhone


    }

    override fun getItemCount(): Int {
        return locList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvtop: TextView = itemView.findViewById(R.id.hosname)
        val tvdis: TextView = itemView.findViewById(R.id.hosloc)
        val tvpres: TextView = itemView.findViewById(R.id.hosphone)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }
}