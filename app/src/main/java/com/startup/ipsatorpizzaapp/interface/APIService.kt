package com.startup.ipsatorpizzaapp.`interface`

import com.startup.ipsatorpizzaapp.data_classes.Pizza_data
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("pizza/1")
    suspend fun getPizzaNested(): Response<Pizza_data>
}