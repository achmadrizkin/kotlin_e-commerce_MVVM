package com.example.kotlin_e_commerce.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        // use this if u use emulator
        val baseUrl = "http://10.0.2.2:3000/v1/"

        fun getRetrofitInstance(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder().baseUrl(baseUrl).client(client.build())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}