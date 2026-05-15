package com.example.hallisanthe

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddProductActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    lateinit var imagePreview: ImageView
    lateinit var selectBtn: Button
    lateinit var uploadBtn: Button

    lateinit var productName: EditText
    lateinit var productPrice: EditText
    lateinit var categorySpinner: Spinner

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_product)

        firestore = FirebaseFirestore.getInstance()

        imagePreview =
            findViewById(R.id.productPreview)

        selectBtn =
            findViewById(R.id.selectImageBtn)

        uploadBtn =
            findViewById(R.id.uploadBtn)

        productName =
            findViewById(R.id.productNameInput)

        productPrice =
            findViewById(R.id.productPriceInput)

        categorySpinner =
            findViewById(R.id.categorySpinner)

        val categories = arrayOf(

            "fruits",
            "vegetables",
            "sarees",
            "crafts",
            "toys"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        categorySpinner.adapter = adapter

        selectBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(intent, 100)
        }

        uploadBtn.setOnClickListener {

            val name =
                productName.text.toString()

            val price =
                productPrice.text.toString()

            val category =
                categorySpinner.selectedItem.toString()

            if(name.isEmpty() || price.isEmpty()){

                Toast.makeText(
                    this,
                    "Enter all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val savedImagePath =

                    if(imageUri != null){

                        saveImageLocally(imageUri!!)

                    } else {

                        ""
                    }

                val description =
                    generateDescription(name)

                val artisanStory =
                    "Handmade by local village artisans with traditional craftsmanship."

                val productMap = hashMapOf(

                    "name" to name,

                    "price" to price,

                    "category" to category,

                    "imageUri" to savedImagePath,

                    "description" to description,

                    "stock" to "In Stock",

                    "quantity" to "1 Kg",

                    "deliveryTime" to "10 mins",

                    "artisanStory" to artisanStory
                )

                firestore.collection("products")
                    .add(productMap)
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Product Uploaded",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            "Upload Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    private fun saveImageLocally(
        uri: Uri
    ): String {

        val inputStream: InputStream? =
            contentResolver.openInputStream(uri)

        val file =
            File(
                filesDir,
                "IMG_${System.currentTimeMillis()}.jpg"
            )

        val outputStream =
            FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()

        outputStream.close()

        return file.absolutePath
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if(requestCode == 100 &&
            resultCode == Activity.RESULT_OK){

            imageUri = data?.data

            imagePreview.setImageURI(imageUri)
        }
    }

    private fun generateDescription(
        productName: String
    ): String {

        return when(productName.lowercase()){

            "mango" ->
                "Fresh farm-picked mangoes sourced directly from local farmers."

            "carrot" ->
                "Organic carrots harvested with natural freshness."

            "apple" ->
                "Fresh and juicy apples directly sourced from farms."

            "pot" ->
                "Handcrafted clay pot made by skilled village artisans."

            "saree" ->
                "Traditional handcrafted saree with authentic Indian design."

            "toy" ->
                "Colorful handmade wooden toys crafted in Channapatna."

            else ->
                "High quality local product from Halli-Santhe marketplace."
        }
    }
}