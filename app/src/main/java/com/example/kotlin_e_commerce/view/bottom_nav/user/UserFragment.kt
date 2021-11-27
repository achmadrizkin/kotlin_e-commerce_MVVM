package com.example.kotlin_e_commerce.view.bottom_nav.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.view.add_products.AddProductActivity
import com.example.kotlin_e_commerce.view.auth.SignInActivity
import com.example.kotlin_e_commerce.view.bottom_nav.my_products.MyProductActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        if (currentUser?.photoUrl != null) {
            Picasso.get().load(currentUser.photoUrl).into(ivUser)
        }

        if (currentUser?.displayName != null) {
            tvUser.text = currentUser.displayName
        }

        if (currentUser?.displayName == null || currentUser?.displayName == "") {
            tvUser.text = "Anonymous"
        }

        tvEmail.text = currentUser?.email

        btnSignOut.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }

        btnAddProduct.setOnClickListener {
            val intent = Intent(activity, AddProductActivity::class.java)
            startActivity(intent)
        }

        btnMyProduct.setOnClickListener {
            val intent = Intent(activity, MyProductActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}