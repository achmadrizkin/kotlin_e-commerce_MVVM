package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.*
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel: ViewModel() {
    lateinit var rcyclerProductList: MutableLiveData<ProductList>
    lateinit var rcyclerBookList: MutableLiveData<BookList>
    lateinit var rcyclerLaptopList: MutableLiveData<BookList>
    lateinit var rcyclerHoodieList: MutableLiveData<BookList>

    // payment -> database
    lateinit var createTransactionLiveData: MutableLiveData<Transaction>

    init {
        rcyclerProductList = MutableLiveData()
        rcyclerBookList = MutableLiveData()
        rcyclerLaptopList = MutableLiveData()
        rcyclerHoodieList = MutableLiveData()

        //
        createTransactionLiveData = MutableLiveData()
    }

    fun getRecyclerProductListObserver(): MutableLiveData<ProductList> {
        return rcyclerProductList
    }

    fun getRecyclerBookListObserver(): MutableLiveData<BookList> {
        return rcyclerBookList
    }

    fun getRecyclerLaptopListObserver(): MutableLiveData<BookList> {
        return rcyclerLaptopList
    }

    fun getRecyclerHoodieListObserver(): MutableLiveData<BookList> {
        return rcyclerHoodieList
    }

    //
    fun getCreateTransactionObservable(): MutableLiveData<Transaction> {
        return createTransactionLiveData
    }

    //
    fun createTransaction(transaction: Transaction) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.createTransaction(transaction)

        //
        call.enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                createTransactionLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                createTransactionLiveData.postValue(null)
            }
        })
    }

    fun getProductList() {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getProductList()

        //
        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    rcyclerProductList.postValue(response.body())
                } else {
                    rcyclerProductList.postValue(null)
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                rcyclerProductList.postValue(null)
            }
        })
    }

    fun getBookList() {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getBookList()

        //
        call.enqueue(object : Callback<BookList> {
            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
                if (response.isSuccessful) {
                    rcyclerBookList.postValue(response.body())
                } else {
                    rcyclerBookList.postValue(null)
                }
            }

            override fun onFailure(call: Call<BookList>, t: Throwable) {
                rcyclerBookList.postValue(null)
            }
        })
    }

    fun getLaptopList() {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getLaptopList()

        //
        call.enqueue(object : Callback<BookList> {
            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
                if (response.isSuccessful) {
                    rcyclerLaptopList.postValue(response.body())
                } else {
                    rcyclerLaptopList.postValue(null)
                }
            }

            override fun onFailure(call: Call<BookList>, t: Throwable) {
                rcyclerLaptopList.postValue(null)
            }
        })
    }

    fun getHoodieList() {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getHoodieList()

        //
        call.enqueue(object : Callback<BookList> {
            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
                if (response.isSuccessful) {
                    rcyclerHoodieList.postValue(response.body())
                } else {
                    rcyclerHoodieList.postValue(null)
                }
            }

            override fun onFailure(call: Call<BookList>, t: Throwable) {
                rcyclerHoodieList.postValue(null)
            }
        })
    }
}