package com.ex.articleapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.articleapp.R
import com.ex.articleapp.adapter.ArticleAdapter
import com.ex.articleapp.data.Article
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

    private var mArticle : List<Article> ?= null


    private var followingList : List<Article> ?= null

    private var articleAdapter: ArticleAdapter ?= null

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

        mArticle = ArrayList()


        fragmentHomeBinding!!.recyclerViewHome.setHasFixedSize(true)

        var linearLayoutManager: RecyclerView.LayoutManager?
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        articleAdapter = context?.let { ArticleAdapter(it, mArticle as ArrayList<Article>) }

        checkFollowing()

        fragmentHomeBinding!!.recyclerViewHome.adapter = articleAdapter





        return view
    }

    private fun checkFollowing() {

        followingList = ArrayList()
            val db = Firebase.firestore
            val ref = db.collection("Follow").document(firebaseAuth.currentUser!!.uid).collection("Following")

        ref.get().addOnSuccessListener {
            result ->

            (followingList as ArrayList<Article>).clear()

            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                document.id.let{
                    (followingList as ArrayList<String>).add(it)
                }
                retrieveAllArticle()
            }

        }.addOnFailureListener{
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveAllArticle() {
        val db = Firebase.firestore
        val ref = db.collection("Articles")

        ref.get().addOnSuccessListener { result ->

            ( mArticle as ArrayList<Article>).clear()
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val article : Article = document.toObject(Article::class.java)
                for(userId in ( mArticle as ArrayList<String>)){
                    if(article.publisher_name == userId.toString()){
                        (mArticle as ArrayList<Article>).add(article)
                    }
                    articleAdapter!!.notifyDataSetChanged()
                }
            }
        }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

    }


}



