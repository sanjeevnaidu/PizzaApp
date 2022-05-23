package com.startup.ipsatorpizzaapp.data_classes

data class Pizza_data(
    val crusts: List<Crust>,
    val defaultCrust: Int,
    val description: String,
    val id: String,
    val isVeg: Boolean,
    val name: String
)