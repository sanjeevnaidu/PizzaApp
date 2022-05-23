package com.startup.ipsatorpizzaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.`interface`.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var btnAddToCart: Button
    /*lateinit var btnAdd: Button
    lateinit var btnRemove: Button
    lateinit var txtCount: TextView*/
    private val BaseURl = "https://625bbd9d50128c570206e502.mockapi.io/api/v1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.list_item_food_item)

        /*btnAdd = findViewById(R.id.btnAdd)
        btnRemove = findViewById(R.id.btnRemove)
        txtCount = findViewById(R.id.txtCount)*/

        /*btnAdd.setOnClickListener {
            var count = txtCount.text.toString().toInt()
            count++
            txtCount.text = count.toString()
        }

        btnRemove.setOnClickListener {
            var count = txtCount.text.toString().toInt()
            if (count!=0){
                count--
                txtCount.text = count.toString()
            }
        }*/

        btnAddToCart = findViewById(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {
            getFoodData()
        }



    }

    override fun onResume() {
        super.onResume()
    }

    private fun getFoodData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPizzaNested()

            withContext(Dispatchers.Main){
                if(response.isSuccessful){

                    val items = response.body()?.crusts
                    if (items != null){
                        for (i in 0 until items.count()){
                            Toast.makeText(this@MainActivity, items[i].name.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    }

    /*private fun getWeatherData() {
        val weatherApi = getString(R.string.WEATHER_API_KEY)
        val finalUrl =
            "http://api.weatherapi.com/v1/current.json?key=$weatherApi&q=Silvassa" //weatherUrl + weatherApi + "&q=" + txtLocation.text.toString() // + "&aqi=no"

        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, finalUrl, {
            //Log.d("response",it)
            try {
                val jsonResponse = JSONObject(it)
                val currentObject = jsonResponse.getJSONObject("current")
                txtTemperature.text = currentObject.getDouble("temp_c").toString() + "°C"
                txtClimate.text =
                    getString(R.string.climate) + " " + currentObject.getJSONObject("condition")
                        .getString("text")
                txtHumidity.text =
                    getString(R.string.humidity) + " " + currentObject.getDouble("humidity")
                        .toString()
                txtWind.text = getString(R.string.wind) + " " + currentObject.getDouble("wind_kph")
                    .toString() + " kph"

                val weatherImageUri =
                    "https:" + currentObject.getJSONObject("condition").getString("icon")
                //Toast.makeText(this, weatherImageUri, Toast.LENGTH_SHORT).show()
                //Glide.with(this).load(Uri.parse(weatherImageUri)).into(imgWeather)
                Picasso.get().load(Uri.parse(weatherImageUri)).into(imgWeather)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(stringRequest)
    }*/
}