package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val text_signUp : TextView = findViewById(R.id.create_one_text)

        text_signUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }
}