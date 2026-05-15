package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    lateinit var recyclerView: RecyclerView
    lateinit var productList: ArrayList<Product>
    lateinit var adapter: ProductAdapter
    lateinit var emptyLayout: LinearLayout

    lateinit var chipAll: Chip
    lateinit var chipToys: Chip
    lateinit var chipCrafts: Chip
    lateinit var chipSarees: Chip
    lateinit var chipFruits: Chip
    lateinit var chipVegetables: Chip

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        firestore = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerView)

        emptyLayout = findViewById(R.id.emptyLayout)

        chipAll = findViewById(R.id.chipAll)
        chipToys = findViewById(R.id.chipToys)
        chipCrafts = findViewById(R.id.chipCrafts)
        chipSarees = findViewById(R.id.chipSarees)
        chipFruits = findViewById(R.id.chipFruits)
        chipVegetables = findViewById(R.id.chipVegetables)

        recyclerView.layoutManager =
            GridLayoutManager(this, 2)

        productList = ArrayList()

        adapter = ProductAdapter(productList)

        recyclerView.adapter = adapter

        loadProducts()

        val searchView =
            findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(

            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(
                    query: String?
                ): Boolean {

                    return false
                }

                override fun onQueryTextChange(
                    newText: String?
                ): Boolean {

                    adapter.filter.filter(newText)

                    return true
                }
            }
        )

        setupFilterChips()

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        val marketBtn =
            findViewById<FloatingActionButton>(
                R.id.marketBtn
            )

        marketBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MarketActivity::class.java
                )
            )
        }

        bottomNav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> {

                    true
                }

                R.id.add -> {

                    startActivity(

                        Intent(
                            this,
                            AddProductActivity::class.java
                        )
                    )

                    true
                }

                R.id.profile -> {

                    startActivity(

                        Intent(
                            this,
                            ProfileActivity::class.java
                        )
                    )

                    true
                }

                else -> false
            }
        }
    }

    private fun loadProducts(){

        productList.clear()

        // Fruits

        productList.add(

            Product(

                name = "Fresh Mango",

                price = "₹150",

                image = R.drawable.mango,

                category = "fruits",

                stock = "In Stock",

                description =
                    "Fresh farm-picked mangoes.",

                quantity = "1 Kg",

                deliveryTime = "10 mins",

                artisanStory =
                    "Directly sourced from farmers."
            )
        )

        productList.add(

            Product(

                name = "Fresh Apple",

                price = "₹180",

                image = R.drawable.apple,

                category = "fruits",

                stock = "In Stock",

                description =
                    "Sweet organic apples.",

                quantity = "1 Kg",

                deliveryTime = "15 mins",

                artisanStory =
                    "Collected from Kashmir farms."
            )
        )

        productList.add(

            Product(

                name = "Fresh Banana",

                price = "₹60",

                image = R.drawable.banana,

                category = "fruits",

                stock = "In Stock",

                description =
                    "Naturally ripened bananas.",

                quantity = "1 Dozen",

                deliveryTime = "10 mins",

                artisanStory =
                    "Directly sourced from village farms."
            )
        )

        // Vegetables

        productList.add(

            Product(

                name = "Fresh Carrot",

                price = "₹50",

                image = R.drawable.carrot,

                category = "vegetables",

                stock = "In Stock",

                description =
                    "Organic healthy carrots.",

                quantity = "500 g",

                deliveryTime = "12 mins",

                artisanStory =
                    "Naturally grown village carrots."
            )
        )

        productList.add(

            Product(

                name = "Fresh Potato",

                price = "₹45",

                image = R.drawable.potato,

                category = "vegetables",

                stock = "In Stock",

                description =
                    "Farm fresh organic potatoes.",

                quantity = "1 Kg",

                deliveryTime = "12 mins",

                artisanStory =
                    "Collected from local farmers."
            )
        )

        productList.add(

            Product(

                name = "Tomato",

                price = "₹40",

                image = R.drawable.tomato,

                category = "vegetables",

                stock = "In Stock",

                description =
                    "Fresh red tomatoes.",

                quantity = "1 Kg",

                deliveryTime = "8 mins",

                artisanStory =
                    "From local vegetable farms."
            )
        )

        // Sarees

        productList.add(

            Product(

                name = "Kanchipuram Saree",

                price = "₹1500",

                image = R.drawable.saree,

                category = "sarees",

                stock = "In Stock",

                description =
                    "Traditional silk saree.",

                quantity = "1 Piece",

                deliveryTime = "1 Day",

                artisanStory =
                    "Handwoven by artisans."
            )
        )

        // Crafts

        productList.add(

            Product(

                name = "Clay Pot",

                price = "₹300",

                image = R.drawable.pot,

                category = "crafts",

                stock = "In Stock",

                description =
                    "Handmade clay pot.",

                quantity = "1 Piece",

                deliveryTime = "20 mins",

                artisanStory =
                    "Made by rural craftsmen."
            )
        )

        // Toys

        productList.add(

            Product(

                name = "Wooden Toy",

                price = "₹450",

                image = R.drawable.toy,

                category = "toys",

                stock = "In Stock",

                description =
                    "Traditional Channapatna toy.",

                quantity = "1 Piece",

                deliveryTime = "25 mins",

                artisanStory =
                    "Made using eco-friendly colors."
            )
        )

        adapter.notifyDataSetChanged()
    }

    private fun setupFilterChips(){

        chipAll.setOnClickListener {

            selectChip(chipAll)

            adapter.filter.filter("")
        }

        chipToys.setOnClickListener {

            selectChip(chipToys)

            adapter.filter.filter("toys")
        }

        chipCrafts.setOnClickListener {

            selectChip(chipCrafts)

            adapter.filter.filter("crafts")
        }

        chipSarees.setOnClickListener {

            selectChip(chipSarees)

            adapter.filter.filter("sarees")
        }

        chipFruits.setOnClickListener {

            selectChip(chipFruits)

            adapter.filter.filter("fruits")
        }

        chipVegetables.setOnClickListener {

            selectChip(chipVegetables)

            adapter.filter.filter("vegetables")
        }
    }

    private fun selectChip(
        selectedChip: Chip
    ){

        val chipList = listOf(

            chipAll,
            chipToys,
            chipCrafts,
            chipSarees,
            chipFruits,
            chipVegetables
        )

        for(chip in chipList){

            chip.setChipBackgroundColorResource(
                android.R.color.white
            )

            chip.setTextColor(

                getColor(
                    android.R.color.black
                )
            )
        }

        selectedChip.setChipBackgroundColorResource(
            R.color.saffron
        )

        selectedChip.setTextColor(

            getColor(
                android.R.color.white
            )
        )
    }
}