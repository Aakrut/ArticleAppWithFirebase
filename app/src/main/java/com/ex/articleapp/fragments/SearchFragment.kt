package com.ex.articleapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.articleapp.R
import com.ex.articleapp.adapter.SearchAdapter
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"

    private lateinit var searchBinding: FragmentSearchBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var mUser : MutableList<User> ?= null

    private  var searchAdapter: SearchAdapter ?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        val view = searchBinding.root

        searchAdapter = context?.let { mUser?.let { it1 -> SearchAdapter(it, it1) } }

        searchBinding.recyclerViewSearch.setHasFixedSize(true)
        searchBinding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        searchBinding.recyclerViewSearch.adapter = searchAdapter

        searchBinding.editTextSearchUser.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
            if(searchBinding.editTextSearchUser.text.toString() == ""){
                searchBinding.cancelSearch.visibility = View.GONE
            }else{
                searchBinding.cancelSearch.visibility = View.VISIBLE
                retrieveUsers(s.toString().toLowerCase())
            }
            }
        })

        searchBinding.cancelSearch.setOnClickListener {
            searchBinding.editTextSearchUser.text.clear()
        }


        return view

    }

    private fun retrieveUsers(input: String) {
        val db = Firebase.firestore

        val ref = db.collection("Users").orderBy("fullName").startAt(input).endAt(input + "\uf8ff").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val user : User = document.toObject(User::class.java)
                if(document != null){
                    mUser!!.add(user)
                }else{
                    mUser!!.clear()
                }
            }
            searchAdapter!!.notifyDataSetChanged()
        }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }


}