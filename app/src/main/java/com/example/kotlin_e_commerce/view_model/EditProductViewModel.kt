package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProductViewModel: ViewModel() {
    lateinit var updateProductLiveData: MutableLiveData<Product>

    init {
        updateProductLiveData = MutableLiveData()
    }

    fun getEditProductObservable(): MutableLiveData<Product> {
        return updateProductLiveData
    }

    fun updateProduct(name_product: String, email_user: String, price: String, product: Product) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.updateProduct(name_product, email_user, price, product)

        //
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful) {
                    updateProductLiveData.postValue(response.body())
                } else {
                    updateProductLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                updateProductLiveData.postValue(null)
            }
        })
    }
}