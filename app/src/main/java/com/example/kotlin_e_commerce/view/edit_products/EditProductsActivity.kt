package com.example.kotlin_e_commerce.view.edit_products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Book
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.view.bottom_nav.BottomNavActivity
import com.example.kotlin_e_commerce.view_model.EditProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_edit_products.*
import kotlinx.android.synthetic.main.activity_edit_products.etDescription
import kotlinx.android.synthetic.main.activity_edit_products.etImageUrl
import kotlinx.android.synthetic.main.activity_edit_products.etNameProduct
import kotlinx.android.synthetic.main.activity_edit_products.etPrice

class EditProductsActivity : AppCompatActivity() {
    lateinit var viewModel: EditProductViewModel
    private lateinit var mAuth: FirebaseAuth
    var selectedItemSpinnner = "Book"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_products)

        //
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        val name_product = intent.getStringExtra("name_product")
        val image_url = intent.getStringExtra("image_url")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")

        etNameProduct.setText(name_product)
        etImageUrl.setText(image_url)
        etDescription.setText(description)
        etPrice.setText(price)

        // spinner
        val arrayCategorySpinner = resources.getStringArray(R.array.category)
        val categorySpinner = findViewById<Spinner>(R.id.category_spinner)
        if (categorySpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCategorySpinner)
            categorySpinner.adapter = adapter
        }

        categorySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedItemSpinnner = arrayCategorySpinner[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        //
        initViewModel()
        editProductObservable()

        btnEditProduct.setOnClickListener {
            // update
            updateProduct(currentUser!!)

            startActivity(Intent(this, BottomNavActivity::class.java))
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EditProductViewModel::class.java)
    }

    private fun editProductObservable() {
        viewModel.getEditProductObservable().observe(this, Observer<Product> {
            if (it == null) {
                Toast.makeText(
                    this@EditProductsActivity,
                    "Failed to update product",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@EditProductsActivity,
                    "Success to update product",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun updateProduct(user: FirebaseUser?) {
        if (user!!.displayName == "") {
            val product = Product(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), "Anonymous", user.email.toString(), selectedItemSpinnner)

            viewModel.updateProduct(etNameProduct.text.toString(), user.email.toString(), etPrice.text.toString(), product)
        } else {
            val product = Product(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), user!!.displayName.toString(), user.email.toString(), selectedItemSpinnner)

            viewModel.updateProduct(etNameProduct.text.toString(), user.email.toString(), etPrice.text.toString(), product)
        }
    }
}