package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ex.articleapp.databinding.ActivityFullArticlerBinding

class FullArticler : AppCompatActivity() {

    private lateinit var fullArticlerBinding : ActivityFullArticlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullArticlerBinding = ActivityFullArticlerBinding.inflate(layoutInflater)
        val view = fullArticlerBinding.root
        setContentView(view)


    }
}