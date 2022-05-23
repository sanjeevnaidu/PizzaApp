package com.startup.ipsatorpizzaapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.data_classes.Size

class CrustSizesAdapter(private val crustSizesList: ArrayList<Size>) :
    RecyclerView.Adapter<CrustSizesAdapter.CrustSizesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrustSizesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_crust_size, parent, false)

        return CrustSizesViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CrustSizesViewHolder, position: Int) {
        val crustSizeDetails: Size = crustSizesList[position]

        holder.name.text = crustSizeDetails.name
        holder.price.text = "Rs. " + crustSizeDetails.price.toString()

        holder.btnAdd.setOnClickListener {
            var count = holder.txtCount.text.toString().toInt()
            count++
            holder.txtCount.text = count.toString()
        }

        holder.btnRemove.setOnClickListener {
            var count = holder.txtCount.text.toString().toInt()
            if (count != 0) {
                count--
                holder.txtCount.text = count.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return crustSizesList.size
    }

    class CrustSizesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txtCrustSize)
        val price: TextView = itemView.findViewById(R.id.txtPrice)

        val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
        val txtCount: TextView = itemView.findViewById(R.id.txtCount)
    }

}