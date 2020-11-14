package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val text_login : TextView = findViewById(R.id.login_text)

        val button : Button = findViewById(R.id.button_next)

        button.setOnClickListener {
            startActivity(Intent(this,InformationActivity::class.java))
        }

        text_login.setOnClickListener {
            startActivity(Intent(this,LogInActivity::class.java))
        }
    }
}