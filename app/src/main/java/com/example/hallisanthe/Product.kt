package com.example.hallisanthe

data class Product(

    val name: String,
    val price: String,
    val image: Int? = null,
    val imageUri: String? = null,
    val category: String = "",
    val artisanStory: String = "",
    val stock: String = "",
    val description: String = "",
    val quantity: String = "",
    val deliveryTime: String = ""


)