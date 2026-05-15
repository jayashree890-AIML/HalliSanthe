package com.example.hallisanthe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)

        val image =
            findViewById<ImageView>(R.id.detailImage)

        val name =
            findViewById<TextView>(R.id.detailName)

        val price =
            findViewById<TextView>(R.id.detailPrice)

        val quantity =
            findViewById<TextView>(R.id.detailQuantity)

        val delivery =
            findViewById<TextView>(R.id.detailDelivery)

        val stock =
            findViewById<TextView>(R.id.detailStock)

        val description =
            findViewById<TextView>(R.id.detailDescription)

        val story =
            findViewById<TextView>(R.id.storyText)

        val cartBtn =
            findViewById<Button>(R.id.cartBtn)

        val whatsappBtn =
            findViewById<Button>(R.id.whatsappBtn)

        name.text =
            intent.getStringExtra("name")

        price.text =
            intent.getStringExtra("price")

        quantity.text =
            "Quantity: " +
                    intent.getStringExtra("quantity")

        delivery.text =
            "Delivery in " +
                    intent.getStringExtra("deliveryTime")

        stock.text =
            intent.getStringExtra("stock")

        description.text =
            intent.getStringExtra("description")

        story.text =
            intent.getStringExtra("story")

        val imageRes =
            intent.getIntExtra("image", 0)

        val imageUri =
            intent.getStringExtra("imageUri")

        if(imageRes != 0){

            image.setImageResource(imageRes)

        } else if(imageUri != null){

            image.setImageURI(Uri.parse(imageUri))
        }

        cartBtn.setOnClickListener {

            Toast.makeText(
                this,
                "Added to Cart",
                Toast.LENGTH_SHORT
            ).show()
        }

        whatsappBtn.setOnClickListener {

            val url =
                "https://wa.me/919876543210"

            val intent =
                Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse(url)

            startActivity(intent)
        }
    }
}