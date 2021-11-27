package com.example.kotlin_e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Product
import com.squareup.picasso.Picasso

class RecyclerViewAdapterProduct(val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapterProduct.MyViewHolder>() {
    var productList = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterProduct.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.card_view_product, parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterProduct.MyViewHolder, position: Int) {
        holder.bind(productList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditClickProduct(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val ivProduct = view.findViewById<ImageView>(R.id.ivProduct)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)

        //
        fun bind(data: Product) {
            tvTitle.text = data.name_product
            tvPrice.text = "Rp" + data.price.toString()

            val url = data.image_url
            Picasso.get().load(url).into(ivProduct)
        }
    }

    interface OnItemClickListener {
        fun onItemEditClickProduct(book : Product)
    }
}