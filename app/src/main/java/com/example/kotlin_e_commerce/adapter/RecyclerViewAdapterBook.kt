package com.example.kotlin_e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Book
import com.squareup.picasso.Picasso

class RecyclerViewAdapterBook(val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapterBook.MyViewHolder>() {
    var bookList = mutableListOf<Book>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterBook.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.card_view_product, parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterBook.MyViewHolder, position: Int) {
        holder.bind(bookList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemClickListenerBook(bookList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val ivProduct = view.findViewById<ImageView>(R.id.ivProduct)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)

        //
        fun bind(data: Book) {
            tvTitle.text = data.name_product
            tvPrice.text = "Rp" + data.price.toString()

            val url = data.image_url
            Picasso.get().load(url).into(ivProduct)
        }
    }

    interface OnItemClickListener {
        fun onItemClickListenerBook(book : Book)
    }
}