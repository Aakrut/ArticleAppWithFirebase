package com.ex.articleapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ex.articleapp.R
import com.ex.articleapp.databinding.FragmentPublishArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PublishArticleFragment : Fragment() {

    private val TAG = "PublishArticleFragment"

    //ViewBinding
    private lateinit var publishArticleBinding: FragmentPublishArticleBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        publishArticleBinding = FragmentPublishArticleBinding.inflate(layoutInflater,container,false)
        val view = publishArticleBinding.root

        firebaseAuth = Firebase.auth

        publishArticleBinding.buttonSave.setOnClickListener {
                publishArticle()
        }

        return view
    }

    //Firebase Publish Article
    private fun publishArticle() {
        if(publishArticleBinding.editTextTitlePublish.text.toString() == ""){
            Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show()
        }else if(publishArticleBinding.editTextAboutPublish.text.toString() == ""){
            Toast.makeText(context, "Please Enter Topic", Toast.LENGTH_SHORT).show()
        }else if(publishArticleBinding.editTextExplanation.text.toString() == ""){
            Toast.makeText(context, "Please Enter Explanation", Toast.LENGTH_SHORT).show()
        }else if(publishArticleBinding.editTextTitlePublish.text.toString() == "" || publishArticleBinding.editTextAboutPublish.text.toString() == ""
                ||publishArticleBinding.editTextExplanation.text.toString() == ""){
            Toast.makeText(context, "Please Enter All The Fields", Toast.LENGTH_SHORT).show()
        }else{
            val title = publishArticleBinding.editTextTitlePublish.text.toString()
            val about = publishArticleBinding.editTextAboutPublish.text.toString()
            val explanation = publishArticleBinding.editTextExplanation.text.toString()

            val db = Firebase.firestore
            val ref = db.collection("Articles").document(firebaseAuth.currentUser!!.uid)

            val hashmap = hashMapOf(
                    "publisher" to firebaseAuth.currentUser!!.uid,
                    "title" to title,
                    "about" to about,
                    "explanation" to explanation
            )

            ref.set(hashmap).addOnSuccessListener {
                Log.d(TAG, "onCreateView: SuccessFully Published")
                Toast.makeText(context, "SuccessFully Published", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Log.d(TAG, "onCreateView: Not Published")
            }
        }
    }


}