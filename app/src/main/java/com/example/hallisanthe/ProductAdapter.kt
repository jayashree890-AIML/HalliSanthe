package com.example.hallisanthe

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ProductAdapter(
    private var productList: ArrayList<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(),
    Filterable {

    private var filteredList = ArrayList<Product>()

    init {

        filteredList = productList
    }

    class ProductViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.productImage)

        val name: TextView =
            itemView.findViewById(R.id.productName)

        val price: TextView =
            itemView.findViewById(R.id.productPrice)

        val stock: TextView =
            itemView.findViewById(R.id.stockText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.product_item,
                    parent,
                    false
                )

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = filteredList[position]

        holder.name.text = product.name

        holder.price.text = product.price

        holder.stock.text = product.stock

        if(product.image != null){

            holder.image.setImageResource(
                product.image
            )

        } else if(
            product.imageUri != null &&
            product.imageUri.isNotEmpty()
        ){

            try {

                holder.image.setImageURI(

                    Uri.fromFile(
                        File(product.imageUri)
                    )
                )

            } catch (e: Exception){

                holder.image.setImageResource(
                    R.drawable.upload_placeholder
                )
            }

        } else {

            holder.image.setImageResource(
                R.drawable.upload_placeholder
            )
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(

                holder.itemView.context,

                ProductDetailActivity::class.java
            )

            intent.putExtra(
                "name",
                product.name
            )

            intent.putExtra(
                "price",
                product.price
            )

            intent.putExtra(
                "description",
                product.description
            )

            intent.putExtra(
                "stock",
                product.stock
            )

            intent.putExtra(
                "quantity",
                product.quantity
            )

            intent.putExtra(
                "deliveryTime",
                product.deliveryTime
            )

            intent.putExtra(
                "story",
                product.artisanStory
            )

            product.image?.let {

                intent.putExtra(
                    "image",
                    it
                )
            }

            product.imageUri?.let {

                intent.putExtra(
                    "imageUri",
                    it
                )
            }

            holder.itemView.context
                .startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return filteredList.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(
                constraint: CharSequence?
            ): FilterResults {

                val searchText =
                    constraint.toString()
                        .lowercase()

                filteredList =

                    if(searchText.isEmpty()){

                        productList

                    } else {

                        val tempList =
                            ArrayList<Product>()

                        for(product in productList){

                            if(

                                product.name
                                    .lowercase()
                                    .contains(searchText)

                                ||

                                product.category
                                    .lowercase()
                                    .contains(searchText)
                            ){

                                tempList.add(product)
                            }
                        }

                        tempList
                    }

                val filterResults =
                    FilterResults()

                filterResults.values =
                    filteredList

                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?
            ) {

                filteredList =
                    results?.values as ArrayList<Product>

                notifyDataSetChanged()
            }
        }
    }
}