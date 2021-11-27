package com.example.kotlin_e_commerce.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.view.bottom_nav.BottomNavActivity
import com.example.kotlin_e_commerce.view.on_boarding.OnBoardingScreenActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            val signInIntent = Intent(this, OnBoardingScreenActivity::class.java)
            startActivity(signInIntent)
            finish()
            if (user != null) {
                val dashboardIntent = Intent(this, BottomNavActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            } else {
                val signInIntent = Intent(this, OnBoardingScreenActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 1000)
    }
}