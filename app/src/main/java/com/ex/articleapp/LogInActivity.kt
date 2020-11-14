package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ex.articleapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var logInBinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        val view = logInBinding.root
        setContentView(view)



        logInBinding.createOneText.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }
}