package com.example.ayusri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ayusri.Models.Disease
import com.example.ayusri.R

class DisAdapter (private var DisList:ArrayList<Disease>):
    RecyclerView.Adapter<DisAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.xdislist, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoc = DisList[position]
        holder.tvtop.text = currentDoc.disTopic
        holder.tvdis.text = currentDoc.disAdd
       // holder.tvpres.text = currentDoc.disAddnew


    }

    override fun getItemCount(): Int {
        return DisList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvtop: TextView = itemView.findViewById(R.id.distop)
        val tvdis: TextView = itemView.findViewById(R.id.disdec)
        //val tvpres: TextView = itemView.findViewById(R.id.dispres)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }
}