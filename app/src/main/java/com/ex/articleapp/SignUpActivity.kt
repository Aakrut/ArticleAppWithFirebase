package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ex.articleapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)



        signUpBinding.buttonNext.setOnClickListener {
            startActivity(Intent(this,InformationActivity::class.java))
        }

        signUpBinding.loginText.setOnClickListener {
            startActivity(Intent(this,LogInActivity::class.java))
        }
    }
}