package com.example.kotlin_e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Book
import com.example.kotlin_e_commerce.model.Transaction
import com.squareup.picasso.Picasso

class RecyclerViewAdapterTransaction(): RecyclerView.Adapter<RecyclerViewAdapterTransaction.MyViewHolder>() {
    var transactionList = mutableListOf<Transaction>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterTransaction.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.card_view_transaction, parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterTransaction.MyViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val ivProduct = view.findViewById<ImageView>(R.id.ivProduct)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)

        //
        fun bind(data: Transaction) {
            tvTitle.text = data.name_product
            tvPrice.text = "Rp" + data.price.toString()

            val url = data.image_url
            Picasso.get().load(url).into(ivProduct)
        }
    }

}