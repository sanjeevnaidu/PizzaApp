package com.startup.ipsatorpizzaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.data_classes.Crust

class CrustsAdapter(private val crustsList: ArrayList<Crust>):
    RecyclerView.Adapter<CrustsAdapter.CrustsViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrustsAdapter.CrustsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.spinner_layout, parent, false)

        return CrustsAdapter.CrustsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CrustsAdapter.CrustsViewHolder, position: Int) {
        val crustItemDetails: Crust = crustsList[position]

        holder.txtCrust.text = crustItemDetails.name
    }

    override fun getItemCount(): Int {
        return crustsList.size
    }

    class CrustsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtCrust: TextView = itemView.findViewById(R.id.txtCrust)
    }
}