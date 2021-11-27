package com.example.kotlin_e_commerce.model

data class Transaction(
    val name_product: String,
    val image_url: String,
    val description: String,
    val price: Int,
    val name_user: String,
    val email_user: String,
    val name_buyer: String,
    val email_buyer: String,
)
