package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = Firebase.auth

        val firebase_user = firebaseAuth.currentUser

        Handler().postDelayed({

          //Checking if User is Logged In or not
            if(firebase_user != null){
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,LogInActivity::class.java))
            }

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}