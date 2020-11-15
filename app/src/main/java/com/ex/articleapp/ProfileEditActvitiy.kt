package com.ex.articleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ex.articleapp.R
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.ActivityProfileEditActvitiyBinding
import com.ex.articleapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileEditActvitiy : AppCompatActivity() {

    private val TAG = "ProfileEditActivity"
    
    private lateinit var profileEditActvitiyBinding: ActivityProfileEditActvitiyBinding
    
    private lateinit var firebaseAuth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileEditActvitiyBinding = ActivityProfileEditActvitiyBinding.inflate(layoutInflater)
        val view = profileEditActvitiyBinding.root
        setContentView(view)
        
        firebaseAuth = Firebase.auth

        var username_text : String = profileEditActvitiyBinding!!.profileEditTextUsername.text.toString()
        var fullname_text = profileEditActvitiyBinding!!.profileEditTextFullName.text.toString()
        var bio_text = profileEditActvitiyBinding!!.profileEditTextBio.text .toString()


        val db = Firebase.firestore
        
        val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        
        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val user : User ?= snapshot.toObject(User::class.java)
                username_text = user!!.username
                fullname_text = user.fullName
                bio_text = user.bio
                Picasso.get().load(user.profile_photo).into(profileEditActvitiyBinding!!.profileEditImageViewCirlce)
                Log.d(TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(TAG, "Current data: null")
            } }


    }


}