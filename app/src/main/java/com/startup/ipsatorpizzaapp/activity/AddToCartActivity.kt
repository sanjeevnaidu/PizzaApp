package com.startup.ipsatorpizzaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.`interface`.APIService
import com.startup.ipsatorpizzaapp.adapter.CrustsAdapter
import com.startup.ipsatorpizzaapp.adapter.FoodItemsAdapter
import com.startup.ipsatorpizzaapp.data_classes.Crust
import com.startup.ipsatorpizzaapp.data_classes.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddToCartActivity : AppCompatActivity() {

    lateinit var txtFoodTitle: TextView
    private val BaseURl = "https://625bbd9d50128c570206e502.mockapi.io/api/v1/"

    private lateinit var crustsList: ArrayList<String>
    private lateinit var crustsAdapter: ArrayAdapter<String>
    private lateinit var crustSizesList: ArrayList<Size>
    private lateinit var crustSizesAdapter: FoodItemsAdapter
    lateinit var spinCrust: AppCompatSpinner
    lateinit var selectedCrust: String

    //lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    //lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        txtFoodTitle = findViewById(R.id.txtFoodTitle)
        spinCrust = findViewById(R.id.spinCrust)
        recyclerView = findViewById(R.id.recyclerViewCrustSizes)

        val position = intent.getIntExtra("position", 0)

        txtFoodTitle.text = intent.getStringExtra("selectedPizza")

        crustsList = arrayListOf()

        getFoodData()

        spinCrust.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCrust = spinCrust.selectedItem.toString()
                    getCrustSizes()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

    }

    private fun getCrustSizes() {

    }

    private fun getFoodData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)

        crustsList.clear()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPizzaNested()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val items = response.body()?.crusts
                    val def = response.body()?.defaultCrust

                    if (items != null) {
                        for (i in 0 until items.count()) {
                            crustsList.add(items[i].name.toString())
                        }
                        crustsAdapter = ArrayAdapter(
                            this@AddToCartActivity,
                            android.R.layout.simple_spinner_dropdown_item,
                            crustsList
                        )

                        spinCrust.adapter = crustsAdapter
                        if (def != null) {
                            spinCrust.setSelection(def.toInt())
                            selectedCrust = spinCrust.selectedItem.toString()
                        }


                    }

                    /*crustSizesList.add(items)
                crustSizesAdapter = FoodItemsAdapter(crustSizesList, this@MainActivity)
                recyclerView.adapter = crustSizesAdapter
                swipeRefreshLayout.isRefreshing = false*/
                    /*for (i in 0 until items.count()){
                    Toast.makeText(this@MainActivity, items[i].name.toString(), Toast.LENGTH_SHORT).show()
                }*/
                }
                /*if (progressBar.isVisible) {
                    progressBar.isVisible = false
                }*/
            }
        }
    }
}