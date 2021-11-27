package com.example.kotlin_e_commerce.view.bottom_nav.my_products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.adapter.RecyclerViewAdapterProduct
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.model.ProductList
import com.example.kotlin_e_commerce.view.bottom_nav.BottomNavActivity
import com.example.kotlin_e_commerce.view.bottom_nav.product_details.ProductDetailsActivity
import com.example.kotlin_e_commerce.view_model.MyProductViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_product.*

class MyProductActivity : AppCompatActivity(), RecyclerViewAdapterProduct.OnItemClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var rcyclerViewAdapter: RecyclerViewAdapterProduct
    private lateinit var viewModel: MyProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_product)

        recyclerView = findViewById(R.id.recyclerViewMyProduct)

        // user
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        initRecyclerView()
        initViewModel(currentUser?.email!!)

        ivBack.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // init recylerview
    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MyProductActivity, 2)

            rcyclerViewAdapter = RecyclerViewAdapterProduct(this@MyProductActivity)
            adapter = rcyclerViewAdapter
        }
    }

    private fun initViewModel(email: String) {
        viewModel = ViewModelProvider(this@MyProductActivity).get(MyProductViewModel::class.java)
        viewModel.getRecyclerMyProductListObserver().observe(this, Observer<ProductList> {
            if (it == null) {
                recyclerViewMyProduct.visibility = View.GONE
                ivNoProduct.visibility = View.VISIBLE
                tvNoProduct.visibility = View.VISIBLE
            } else {
                rcyclerViewAdapter.productList = it.data.toMutableList()
                rcyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getMyProductList(email)
    }

    override fun onItemEditClickProduct(product: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java)

        intent.putExtra("name_product", product.name_product)
        intent.putExtra("image_url", product.image_url)
        intent.putExtra("description", product.description)
        intent.putExtra("price", product.price.toString())
        intent.putExtra("name_user", product.name_user)
        intent.putExtra("email_user", product.email_user)

        startActivityForResult(intent, 1000)
    }
}