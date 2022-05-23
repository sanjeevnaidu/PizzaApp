package com.startup.ipsatorpizzaapp.adapter

import android.annotation.SuppressLint
import android.content.Context
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

    private var adapterCallback: AdapterCallback? = null

    fun CrustSizesAdapter(context: Context) {
        adapterCallback = try {
            context as AdapterCallback
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement AdapterCallback.")
        }
    }

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
            if (count == 1){
                try {
                    adapterCallback?.onMethodCallback()
                } catch (e: java.lang.ClassCastException) {
                    // do something
                }
            }
        }

        holder.btnRemove.setOnClickListener {
            var count = holder.txtCount.text.toString().toInt()
            if (count != 0) {
                count--
                holder.txtCount.text = count.toString()
                if (count == 0){
                    try {
                        adapterCallback?.onMethodCallback()
                    } catch (e: java.lang.ClassCastException) {
                        // do something
                    }
                }
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

    interface AdapterCallback {
        fun onMethodCallback()
    }

}