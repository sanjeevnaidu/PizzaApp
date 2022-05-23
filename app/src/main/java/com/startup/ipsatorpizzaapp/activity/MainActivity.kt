package com.startup.ipsatorpizzaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.startup.ipsatorpizzaapp.R
import com.startup.ipsatorpizzaapp.`interface`.APIService
import com.startup.ipsatorpizzaapp.adapter.FoodItemsAdapter
import com.startup.ipsatorpizzaapp.data_classes.Pizza_data
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

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    private lateinit var foodItemsList: ArrayList<Pizza_data>
    private lateinit var foodItemsAdapter: FoodItemsAdapter

    //private lateinit var imgPullIndicator: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerViewFoodItems)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //imgPullIndicator = findViewById(R.id.imgPullIndicator)

        progressBar.isVisible = true

        foodItemsList = arrayListOf()

        swipeRefreshLayout.setOnRefreshListener {
            getFoodData()
        }

        /*val bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetMain))
            .apply {
                peekHeight = 130
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Do something for new state.
                //Toast.makeText(this@MainActivity, newState.toString(), Toast.LENGTH_SHORT).show()
                imgPullIndicator.isVisible = newState != 3
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Do something for slide offset.
                //Toast.makeText(this@MainActivity, "Bottom Sheet is sliding", Toast.LENGTH_SHORT).show()
            }
        }

        // To add the callback:
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)*/

        // To remove the callback:
        //bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)

    }

    override fun onResume() {
        getFoodData()
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    private fun getFoodData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)

        foodItemsList.clear()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPizzaNested()

            withContext(Dispatchers.Main){
                if(response.isSuccessful){

                    val items = response.body()
                    if (items != null){
                        foodItemsList.add(items)
                        foodItemsAdapter = FoodItemsAdapter(foodItemsList)
                        recyclerView.adapter = foodItemsAdapter
                        swipeRefreshLayout.isRefreshing = false
                        /*for (i in 0 until items.count()){
                            Toast.makeText(this@MainActivity, items[i].name.toString(), Toast.LENGTH_SHORT).show()
                        }*/
                    } else {
                        foodItemsAdapter = FoodItemsAdapter(foodItemsList)
                        recyclerView.adapter = foodItemsAdapter
                        swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this@MainActivity, response.code().toString() , Toast.LENGTH_SHORT).show()
                    }

                } else {
                    foodItemsAdapter = FoodItemsAdapter(foodItemsList)
                    recyclerView.adapter = foodItemsAdapter
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this@MainActivity, response.code().toString() , Toast.LENGTH_SHORT).show()
                }
            }
            if (progressBar.isVisible) {
                progressBar.isVisible = false
            }
        }
    }
}