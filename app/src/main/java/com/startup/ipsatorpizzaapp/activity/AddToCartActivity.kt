package com.startup.ipsatorpizzaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.`interface`.APIService
import com.startup.ipsatorpizzaapp.adapter.CrustSizesAdapter
import com.startup.ipsatorpizzaapp.data_classes.Crust
import com.startup.ipsatorpizzaapp.data_classes.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddToCartActivity : AppCompatActivity(), CrustSizesAdapter.AdapterCallback {

    lateinit var txtFoodTitle: TextView
    lateinit var imgIsVeg: ImageView
    lateinit var btnAddCustomPizza: Button
    private val BaseURl = "https://625bbd9d50128c570206e502.mockapi.io/api/v1/"

    private lateinit var crustsList: ArrayList<String>
    private lateinit var crustsAdapter: ArrayAdapter<String>
    private lateinit var crustSizesList: ArrayList<Size>
    private lateinit var crustSizesAdapter: CrustSizesAdapter
    lateinit var spinCrust: AppCompatSpinner
    lateinit var selectedCrust: String
    private lateinit var items: List<Crust>

    //lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    //lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        txtFoodTitle = findViewById(R.id.txtFoodTitle)
        imgIsVeg = findViewById(R.id.imgIsVeg)
        btnAddCustomPizza = findViewById(R.id.btnAddCustomPizza)
        spinCrust = findViewById(R.id.spinCrust)
        recyclerView = findViewById(R.id.recyclerViewCrustSizes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val position = intent.getIntExtra("position", 0)
        val isVeg = intent.getBooleanExtra("isVeg", true)

        txtFoodTitle.text = intent.getStringExtra("selectedPizza")

        if (!isVeg){
            imgIsVeg.setBackgroundResource(R.drawable.background_is_nonveg)
        }

        crustsList = arrayListOf()
        crustSizesList = arrayListOf()

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
                    getCrustSizes(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

    }

    private fun getCrustSizes(position: Int) {
        crustSizesList.clear()
        for (i in 0 until items[position].sizes.size)
            crustSizesList.add(items[position].sizes[i])
        crustSizesAdapter = CrustSizesAdapter(crustSizesList)
        recyclerView.adapter = crustSizesAdapter
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

                    items = response.body()!!.crusts
                    val def = response.body()?.defaultCrust

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

                    for (i in 0 until items[def!!.toInt()].sizes.size){
                        crustSizesList.add(items[def].sizes[i])

                    }
                    crustSizesAdapter = CrustSizesAdapter(crustSizesList)
                    recyclerView.adapter = crustSizesAdapter

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

    override fun onMethodCallback() {
        btnAddCustomPizza.isVisible = true
    }
}