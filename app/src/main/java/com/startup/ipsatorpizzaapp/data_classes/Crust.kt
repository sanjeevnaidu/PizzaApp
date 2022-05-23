package com.startup.ipsatorpizzaapp.data_classes

data class Crust(
    val defaultSize: Int,
    val id: Int,
    val name: String,
    val sizes: List<Size>
)