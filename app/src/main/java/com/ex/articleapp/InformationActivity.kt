package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ex.articleapp.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    private lateinit var informationBinding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        informationBinding = ActivityInformationBinding.inflate(layoutInflater)
        val view = informationBinding.root
        setContentView(view)

        informationBinding.buttonSignup.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }


    }
}