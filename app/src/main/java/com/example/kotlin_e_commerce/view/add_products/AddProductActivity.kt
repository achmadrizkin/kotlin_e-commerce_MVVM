package com.example.kotlin_e_commerce.view.add_products

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Book
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.view.bottom_nav.BottomNavActivity
import com.example.kotlin_e_commerce.view.bottom_nav.home.HomeFragment
import com.example.kotlin_e_commerce.view.bottom_nav.my_products.MyProductActivity
import com.example.kotlin_e_commerce.view_model.AddProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var viewModel: AddProductViewModel
    var selectedItemSpinnner = "Book";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        initViewModel()
        createProductObservable()
        createLaptopObservable()
        createHoodieObservable()
        createBookObservable()

        // spinner\
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

        btnAddProduct.setOnClickListener {
            addProduct(currentUser!!)

            startActivity(Intent(this, BottomNavActivity::class.java))
        }

        ivBack.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
    }

    private fun addProduct(user: FirebaseUser?) {
        if (user!!.displayName == "") {
            val product = Product(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), "Anonymous", user.email.toString(), selectedItemSpinnner)
            val book = Book(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), "Anonymous", user.email.toString())

            viewModel.addProduct(product)
            addByCategory(book)  // add to category
        } else {
            val product = Product(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), user!!.displayName.toString(), user.email.toString(), selectedItemSpinnner)
            val book = Book(etNameProduct.text.toString(),etImageUrl.text.toString(), etDescription.text.toString(), Integer.parseInt(etPrice.text.toString()), "Anonymous", user.email.toString())

            viewModel.addProduct(product)
            addByCategory(book)   // add to category
        }
    }

    private fun addByCategory(book: Book) {
        if (selectedItemSpinnner == "Book") {
            viewModel.addBook(book)
        } else if(selectedItemSpinnner == "Hoodie"){
            viewModel.addHoodie(book)
        } else if(selectedItemSpinnner == "Laptop") {
            viewModel.addLaptop(book)
        }
    }

    private fun createProductObservable() {
        viewModel.getAddProductObservable().observe(this, Observer<Product> {})
    }

    private fun createLaptopObservable() {
        viewModel.getAddLaptopObservable().observe(this, Observer<Book> {
            if (it == null) {
                Toast.makeText(
                    this@AddProductActivity,
                    "Failed to create category laptop",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    "Success to create category laptop",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun createHoodieObservable() {
        viewModel.getAddHoodieObservable().observe(this, Observer<Book> {
            if (it == null) {
                Toast.makeText(
                    this@AddProductActivity,
                    "Failed to create category hoodie",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    "Success to create category hoodie",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun createBookObservable() {
        viewModel.getAddBookObservable().observe(this, Observer<Book> {
            if (it == null) {
                Toast.makeText(
                    this@AddProductActivity,
                    "Failed to create category hoodie",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    "Success to create category hoodie",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }
}