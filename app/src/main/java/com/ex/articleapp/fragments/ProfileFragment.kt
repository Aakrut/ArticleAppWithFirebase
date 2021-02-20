package com.ex.articleapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.articleapp.FullArticler
import com.ex.articleapp.LogInActivity
import com.ex.articleapp.ProfileEditActvitiy
import com.ex.articleapp.adapter.ProfileRecyClerAdapter
import com.ex.articleapp.data.Article
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlin.math.log


class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"

    private  var profileBinding: FragmentProfileBinding ?= null
    private val binding  get() = profileBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    private var mArticle : MutableList<Article> ?= null

    private var profileRecyClerAdapter :ProfileRecyClerAdapter ?= null


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

        mArticle = ArrayList()



        //Firebase FireStore Reference
        val ref= db.collection("Users").document(firebaseAuth.currentUser!!.uid)


        profileRecyClerAdapter = context?.let { ProfileRecyClerAdapter(it,
            mArticle as ArrayList<Article>
        ) }
        profileBinding!!.recyclerViewArticlesProfile.setHasFixedSize(true)
        profileBinding!!.recyclerViewArticlesProfile.layoutManager = LinearLayoutManager(context)
        profileBinding!!.recyclerViewArticlesProfile.adapter = profileRecyClerAdapter



        ref.addSnapshotListener {  snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                val user: User? = snapshot.toObject(User::class.java)

                profileBinding!!.profileTextUsername.text = user!!.username
                profileBinding!!.profileTextFullName.text = user.fullName
                profileBinding!!.profileTextBio.text = user.bio
                Picasso.get().load(user.profile_photo).into(profileBinding!!.profileImageView)


                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            }  }

        profileBinding!!.moreVert.setOnClickListener {
            startActivity(Intent(context,ProfileEditActvitiy::class.java))
        }

        //LogOut Button
        profileBinding!!.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context,LogInActivity::class.java))

        }

            db.collection("Articles Author").document(Firebase.auth.currentUser!!.uid)
                .collection("Article").get().addOnSuccessListener {documents ->
                        (mArticle as ArrayList<Article>).clear()
                    for(document in documents){
                        Log.d(TAG, "onCreateView: $document")
                        val article : Article = document.toObject(Article::class.java)
                        if(article.publisher == Firebase.auth.currentUser!!.uid){

                            (mArticle as ArrayList<Article>).add(article)
                        }
                        profileRecyClerAdapter?.notifyDataSetChanged()
                    }
                }.addOnFailureListener{
                    error ->
                    Log.d(TAG, "onCreateView: $error ")
                }
        return view
    }

}