package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.ex.articleapp.adapter.ProfileRecyClerAdapter
import com.ex.articleapp.data.Article
import com.ex.articleapp.databinding.ActivityFullArticlerBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class FullArticler : AppCompatActivity() {

    val TAG = "FullArticle"

    private lateinit var fullArticlerBinding : ActivityFullArticlerBinding

    private  var profileRecyClerAdapter: ProfileRecyClerAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        fullArticlerBinding = ActivityFullArticlerBinding.inflate(layoutInflater)
        val view = fullArticlerBinding.root
        setContentView(view)



        val db = Firebase.firestore

        db.collection("Articles").get().addOnSuccessListener {
            document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document}")
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { 
            error ->
            Log.d(TAG, "onCreate: No Doc")
        }


    }
}










