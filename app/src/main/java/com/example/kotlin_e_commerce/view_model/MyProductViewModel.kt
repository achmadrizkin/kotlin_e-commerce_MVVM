package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.ProductList
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProductViewModel: ViewModel() {
    lateinit var recyclerMyProductList: MutableLiveData<ProductList>

    init {
        recyclerMyProductList = MutableLiveData()
    }

    fun getRecyclerMyProductListObserver(): MutableLiveData<ProductList> {
        return recyclerMyProductList
    }

    fun getMyProductList(email: String) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getMyProduct(email)

        //
        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    recyclerMyProductList.postValue(response.body())
                } else {
                    recyclerMyProductList.postValue(null)
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                recyclerMyProductList.postValue(null)
            }
        })
    }
}