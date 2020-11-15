package com.ex.articleapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ex.articleapp.R
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    //ViewBinding
    private var fragmentHomeBinding: FragmentHomeBinding ?= null

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = fragmentHomeBinding!!.root

        firebaseAuth = Firebase.auth

        //initialize DataBase
        val db = Firebase.firestore

        //Reference Firebase FireStore
        val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid)

        ref.addSnapshotListener{
            snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                val user : User ? = snapshot.toObject(User::class.java)
                fragmentHomeBinding!!.usernameText.text = user!!.username
                Picasso.get().load(user.profile_photo).into(fragmentHomeBinding!!.circleImageProfileHome)
                Log.d(TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(TAG, "Current data: null")
            }
        }





        return view
    }


}