package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ex.articleapp.R
import com.ex.articleapp.databinding.ActivityMainBinding
import com.ex.articleapp.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var homeFragment : HomeFragment
    private lateinit var searchFragment : SearchFragment
    private lateinit var publishArticleFragment : PublishArticleFragment
    private lateinit var notificationFragment : NotificationFragment
    private lateinit var profileFragment : ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        //Fragments
            homeFragment = HomeFragment()
            searchFragment = SearchFragment()
            publishArticleFragment = PublishArticleFragment()
            notificationFragment = NotificationFragment()
            profileFragment = ProfileFragment()


        loadFragment(homeFragment)

        //BottomNavigation
        bottomNavigation()

    }

    private fun bottomNavigation() {
        mainBinding.bottomNav.setOnNavigationItemSelectedListener {
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