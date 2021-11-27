package com.example.kotlin_e_commerce.view.on_boarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.adapter.OnBoardingViewPagerAdapter
import com.example.kotlin_e_commerce.model.OnBoardingData
import com.example.kotlin_e_commerce.view.auth.SignInActivity
import com.google.android.material.tabs.TabLayout

class OnBoardingScreenActivity : AppCompatActivity() {
    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var next: TextView? = null
    var position = 0
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // check if app already running once time
        if (restorePrefData()) {
            val i = Intent(applicationContext, SignInActivity::class.java)
            startActivity(i)
        }

        setContentView(R.layout.activity_on_boarding_screen)

        tabLayout = findViewById(R.id.tab_indicator)
        next = findViewById(R.id.next)

        // add data to model class
        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(
            OnBoardingData(
                "Discount up to 73%",
                "Lorem ipsum dolor sit amet\nconsectetur adipiscing elit.",
                R.drawable.ob1
            )
        )
        onBoardingData.add(
            OnBoardingData(
                "Choose and checkout",
                "Lorem ipsum dolor sit amet\nconsectetur adipiscing elit.",
                R.drawable.ob2
            )
        )
        onBoardingData.add(
            OnBoardingData(
                "Secure Payment",
                "Lorem ipsum dolor sit amet\nconsectetur adipiscing elit.",
                R.drawable.ob3
            )
        )

        setOnBoardingViewPagerAdapter(onBoardingData)
        position = onBoardingViewPager!!.currentItem

        //
        next?.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size) {
                savePrefData()
                val i = Intent(applicationContext, SignInActivity::class.java)
                startActivity(i)
            }
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size - 1) {
                    next!!.text = "Get Started"
                } else {
                    next!!.text = "Next"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>) {
        onBoardingViewPager = findViewById(R.id.screemPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter

        // set tab layout
        tabLayout?.setupWithViewPager(onBoardingViewPager)

    }

    // add shared pref and store boolean (check if u are first time running app or not)
    private fun savePrefData() {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)

        return sharedPreferences!!.getBoolean("isFirstTimeRun", false)
    }

}