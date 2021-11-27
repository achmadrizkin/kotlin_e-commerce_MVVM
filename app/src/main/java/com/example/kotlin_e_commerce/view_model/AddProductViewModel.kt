package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.Book
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductViewModel: ViewModel() {
    lateinit var addProductLiveData: MutableLiveData<Product>
    lateinit var addBookLiveData: MutableLiveData<Book>
    lateinit var addLaptopLiveData: MutableLiveData<Book>
    lateinit var addHoodieLiveData: MutableLiveData<Book>

    init {
        addProductLiveData = MutableLiveData()
        addBookLiveData = MutableLiveData()
        addLaptopLiveData = MutableLiveData()
        addHoodieLiveData = MutableLiveData()
    }

    fun getAddProductObservable(): MutableLiveData<Product> {
        return addProductLiveData
    }

    fun getAddBookObservable(): MutableLiveData<Book> {
        return addBookLiveData
    }

    fun getAddLaptopObservable(): MutableLiveData<Book> {
        return addLaptopLiveData
    }

    fun getAddHoodieObservable(): MutableLiveData<Book> {
        return addHoodieLiveData
    }

    fun addProduct(product: Product) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.addProduct(product)

        //
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                addProductLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                addProductLiveData.postValue(null)
            }
        })
    }

    fun addBook(book: Book) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.addBook(book)

        //
        call.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addBookLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addBookLiveData.postValue(null)
            }
        })
    }

    fun addLaptop(laptop: Book) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.addLaptop(laptop)

        //
        call.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addLaptopLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addLaptopLiveData.postValue(null)
            }
        })
    }

    fun addHoodie(hoodie: Book) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.addHoodie(hoodie)

        //
        call.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addHoodieLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addHoodieLiveData.postValue(null)
            }
        })
    }
}