package com.example.kotlin_e_commerce.view.bottom_nav.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.adapter.RecyclerViewAdapterBook
import com.example.kotlin_e_commerce.adapter.RecyclerViewAdapterProduct
import com.example.kotlin_e_commerce.model.*
import com.example.kotlin_e_commerce.view.bottom_nav.product_details.ProductDetailsActivity
import com.example.kotlin_e_commerce.view_model.HomeFragmentViewModel
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), RecyclerViewAdapterProduct.OnItemClickListener, RecyclerViewAdapterBook.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView

    private lateinit var rcylerProductList: RecyclerViewAdapterProduct
    private lateinit var rcylerBookList: RecyclerViewAdapterBook
    private lateinit var rcylerLaptopList: RecyclerViewAdapterBook
    private lateinit var rcylerHoodieList: RecyclerViewAdapterBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        // Recycler View
        initGetAllProductRecyclerView(view)
        initGetBookRecyclerView(view)
        initGetLaptopRecyclerView(view)
        initGetHoodieRecyclerView(view)

        getAllProduct()
        getBookList()
        getLaptopList()
        getHoodieList()

        return view
    }

    private fun initGetAllProductRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        rcylerProductList = RecyclerViewAdapterProduct(this@HomeFragment)
        recyclerView.adapter = rcylerProductList
    }

    private fun initGetBookRecyclerView(view: View) {
        recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView2.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true);
        recyclerView2.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        rcylerBookList = RecyclerViewAdapterBook(this@HomeFragment)
        recyclerView2.adapter = rcylerBookList
    }

    private fun initGetLaptopRecyclerView(view: View) {
        recyclerView3 = view.findViewById<RecyclerView>(R.id.recyclerView3)
        recyclerView3.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
        recyclerView3.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        rcylerLaptopList = RecyclerViewAdapterBook(this@HomeFragment)
        recyclerView3.adapter = rcylerLaptopList
    }

    private fun initGetHoodieRecyclerView(view: View) {
        recyclerView4 = view.findViewById<RecyclerView>(R.id.recyclerView4)
        recyclerView4.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
        recyclerView4.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        rcylerHoodieList = RecyclerViewAdapterBook(this@HomeFragment)
        recyclerView4.adapter = rcylerHoodieList
    }

    private fun getAllProduct() {
        val viewModelProduct = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //TODO: WATCH THIS CODE (WARNINGG)
        viewModelProduct.getRecyclerProductListObserver().observe(viewLifecycleOwner, Observer<ProductList> {
            if (it != null) {
                rcylerProductList.productList = it.data.toMutableList()
                rcylerProductList.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelProduct.getProductList()
    }

    private fun getBookList() {
        val viewModelBook = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //TODO: WATCH THIS CODE (WARNINGG)
        viewModelBook.getRecyclerBookListObserver().observe(viewLifecycleOwner, Observer<BookList> {
            if (it != null) {
                if (::rcylerBookList.isInitialized) {
                    rcylerBookList.bookList = it.data.toMutableList()
                    rcylerBookList.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelBook.getBookList()
    }

    private fun getLaptopList() {
        val viewModelBook = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //TODO: WATCH THIS CODE (WARNINGG)
        viewModelBook.getRecyclerLaptopListObserver().observe(viewLifecycleOwner, Observer<BookList> {
            if (it != null) {
                if (::rcylerLaptopList.isInitialized) {
                    rcylerLaptopList.bookList = it.data.toMutableList()
                    rcylerLaptopList.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelBook.getLaptopList()
    }

    private fun getHoodieList() {
        val viewModelBook = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //TODO: WATCH THIS CODE (WARNINGG)
        viewModelBook.getRecyclerHoodieListObserver().observe(viewLifecycleOwner, Observer<BookList> {
            if (it != null) {
                if (::rcylerHoodieList.isInitialized) {
                    rcylerHoodieList.bookList = it.data.toMutableList()
                    rcylerHoodieList.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelBook.getHoodieList()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onItemEditClickProduct(product: Product) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)

        intent.putExtra("name_product", product.name_product)
        intent.putExtra("image_url", product.image_url)
        intent.putExtra("description", product.description)
        intent.putExtra("price", product.price.toString())
        intent.putExtra("name_user", product.name_user)
        intent.putExtra("email_user", product.email_user)

        startActivityForResult(intent, 1000)
    }

    override fun onItemClickListenerBook(book: Book) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)

        intent.putExtra("name_product", book.name_product)
        intent.putExtra("image_url", book.image_url)
        intent.putExtra("description", book.description)
        intent.putExtra("price", book.price.toString())
        intent.putExtra("name_user", book.name_user)
        intent.putExtra("email_user", book.email_user)

        startActivityForResult(intent, 1000)
    }
}