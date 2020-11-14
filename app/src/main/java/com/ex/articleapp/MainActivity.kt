package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ex.articleapp.R
import com.ex.articleapp.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Fragments
        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val publishArticleFragment = PublishArticleFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()


        loadFragment(homeFragment)

        //BottomNavigation

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    loadFragment(searchFragment)
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.publish -> {
                    loadFragment(publishArticleFragment)
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.notificaion -> {
                    loadFragment(notificationFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    loadFragment(profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }


    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}