package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsViewModel: ViewModel() {
    lateinit var deleteProductLiveData: MutableLiveData<Product>

    init {
        deleteProductLiveData = MutableLiveData()
    }

    fun getDeleteProductObservable(): MutableLiveData<Product> {
        return deleteProductLiveData
    }

    fun deleteProduct(name_product: String, email_user: String, price: String) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.deleteProduct(name_product, email_user, price)

        //
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful) {
                    deleteProductLiveData.postValue(response.body())
                } else {
                    deleteProductLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                deleteProductLiveData.postValue(null)
            }
        })
    }
}