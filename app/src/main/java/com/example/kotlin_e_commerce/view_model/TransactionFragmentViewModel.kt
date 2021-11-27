package com.example.kotlin_e_commerce.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_e_commerce.model.*
import com.example.kotlin_e_commerce.service.RetrofitInstance
import com.example.kotlin_e_commerce.service.RetrofitService
import com.example.kotlin_e_commerce.view.auth.SignInActivity
import com.example.kotlin_e_commerce.view.bottom_nav.user.UserFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragmentViewModel: ViewModel() {
    lateinit var transactionList: MutableLiveData<TransactionList>

    init {
        transactionList = MutableLiveData()
    }

    fun getRecyclerTransactionListObserver(): MutableLiveData<TransactionList> {
        return transactionList
    }

    fun getTransactionList(email_buyer: String?) {
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getTransactionList(email_buyer!!)

        //
        call.enqueue(object : Callback<TransactionList?> {
            override fun onResponse(call: Call<TransactionList?>, response: Response<TransactionList?>) {
                if (response.isSuccessful) {
                    transactionList.postValue(response.body())
                } else {
                    transactionList.postValue(null)
                }
            }

            override fun onFailure(call: Call<TransactionList?>, t: Throwable) {
                transactionList.postValue(null)
            }
        })
    }
}