package com.startup.ipsatorpizzaapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.activity.AddToCartActivity
import com.startup.ipsatorpizzaapp.data_classes.Pizza_data

class FoodItemsAdapter(private val foodItemsList: ArrayList<Pizza_data>) :
    RecyclerView.Adapter<FoodItemsAdapter.FoodItemsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_food_item, parent, false)

        return FoodItemsViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: FoodItemsViewHolder, position: Int) {
        val foodItemDetails: Pizza_data = foodItemsList[position]

        holder.name.text = foodItemDetails.name
        holder.description.text = foodItemDetails.description

        holder.btnAdd.setOnClickListener {
            com.google.android.material.snackbar.Snackbar.make(
                holder.itemView,
                foodItemDetails.name,
                com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
            ).show()

            val intent = Intent(holder.itemView.context, AddToCartActivity::class.java)
            intent.putExtra("selectedPizza", foodItemDetails.name)
            intent.putExtra("position", position)

            holder.itemView.context.startActivity(intent)
        }

        if (!foodItemDetails.isVeg){
            holder.imgIsVeg.setBackgroundResource(R.drawable.background_is_nonveg)
        }


    }

    override fun getItemCount(): Int {
        return foodItemsList.size
    }

    class FoodItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txtFoodTitle)
        val description: TextView = itemView.findViewById(R.id.txtFoodDescription)
        val btnAdd: Button = itemView.findViewById(R.id.btnAddToCart)
        val imgIsVeg: ImageView = itemView.findViewById(R.id.imgIsVeg)

    }

}