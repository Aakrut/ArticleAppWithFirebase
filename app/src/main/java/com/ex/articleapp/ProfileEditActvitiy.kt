package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ex.articleapp.R
import com.ex.articleapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileEditActvitiy : AppCompatActivity() {

    private val TAG = "ProfileEditActivity"
    
    private lateinit var profileBinding: FragmentProfileBinding
    
    private lateinit var firebaseAuth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = FragmentProfileBinding.inflate(layoutInflater)
        val view = profileBinding.root
        setContentView(view)
        
        firebaseAuth = Firebase.auth
        
        val db = Firebase.firestore
        
        val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        
        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            } }


    }


}