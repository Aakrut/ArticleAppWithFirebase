package com.ex.articleapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.articleapp.adapter.ArticleAdapter
import com.ex.articleapp.data.Article
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    //ViewBinding
    private var fragmentHomeBinding: FragmentHomeBinding?= null

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    private var mArticle : MutableList<Article> ?= null

    private var followingList : MutableList<Article> ?= null

    private var articleAdapter: ArticleAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val view = fragmentHomeBinding!!.root

        firebaseAuth = Firebase.auth

        mArticle = ArrayList()

        articleAdapter = context?.let { ArticleAdapter(it, mArticle as ArrayList<Article>) }

        fragmentHomeBinding!!.recyclerViewHome.setHasFixedSize(true)
        fragmentHomeBinding!!.recyclerViewHome.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding!!.recyclerViewHome.adapter = articleAdapter

            checkFollowing()



        return view
    }

    //Firebase

    //For Whom You Are Following and That User Article Will Show Up

    private fun checkFollowing() {

        followingList = ArrayList()

        val db = Firebase.firestore

        val reff = db.collection("Follow").document(firebaseAuth.currentUser!!.uid).collection("Following")

        reff.get().addOnSuccessListener {
            result ->

            (followingList as ArrayList<String>).clear()

            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")

                    (followingList as ArrayList<String>).add(document.id)


                    retrieveAllArticle()

            }

        }.addOnFailureListener{
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }

    }

    //Retrieving All Information About Article That Published
    private fun retrieveAllArticle() {
        val db = Firebase.firestore
        val ref = db.collection("Articles")

        ref.get().addOnSuccessListener { result ->

            mArticle!!.clear()
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val article : Article = document.toObject(Article::class.java)
                mArticle!!.add(article)
                articleAdapter!!.notifyDataSetChanged()
            }
        }.addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }

}



