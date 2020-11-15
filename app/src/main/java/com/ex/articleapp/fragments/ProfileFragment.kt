package com.ex.articleapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ex.articleapp.ProfileEditActvitiy
import com.ex.articleapp.R
import com.ex.articleapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"

    private  var profileBinding: FragmentProfileBinding ?= null
    private val binding  get() = profileBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding!!.root

        firebaseAuth = Firebase.auth

        //Database Initialize
        val db = Firebase.firestore

        //Firebase FireStore Reference
        val ref= db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        
        ref.addSnapshotListener {  snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            }  }


        profileBinding!!.moreVert.setOnClickListener {
            startActivity(Intent(context,ProfileEditActvitiy::class.java))
        }





        return view
    }


}