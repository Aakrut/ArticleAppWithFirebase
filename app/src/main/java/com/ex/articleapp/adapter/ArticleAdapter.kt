package com.ex.articleapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.articleapp.R
import com.ex.articleapp.data.Article
import com.ex.articleapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ArticleAdapter(val context : Context,val mArticle : List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    private val TAG = "ArticleAdapter"

    //FirebaseAuth
    private lateinit var firebaseAuth : FirebaseAuth
    
    class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var circle_image_home : CircleImageView ? = null
        var username_text : TextView ?= null
        var title_text : TextView ?= null
        var about_text : TextView ?= null
        var explanation_text : TextView ?= null
        var publishername : TextView ?= null
        var comment_btn : ImageView ?= null

        init {
            circle_image_home = itemView.findViewById(R.id.circle_image_home_item_list)
            username_text = itemView.findViewById(R.id.text_home_username_item_list)
            title_text = itemView.findViewById(R.id.text_home_title_item_list)
            about_text = itemView.findViewById(R.id.text_home_topic_item_list)
            explanation_text = itemView.findViewById(R.id.text_home_explanation_item_list)
            publishername = itemView.findViewById(R.id.text_home_fullname_item_list)
            comment_btn = itemView.findViewById(R.id.comment_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item_list,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val current_item : Article = mArticle[position]

        holder.title_text!!.text = current_item.title
        holder.about_text!!.text = current_item.about
        holder.explanation_text!!.text = current_item.explanation


        
        holder.comment_btn!!.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: Comment Button Clicked")
        }
            
        publishInfo(holder.circle_image_home,holder.username_text,holder.publishername,current_item.publisher)
        
    }

   

    override fun getItemCount(): Int {
        return mArticle.size
    }

    private fun publishInfo(circleImageHome: CircleImageView?, usernameText: TextView?,publishername : TextView?, publisher: String) {
            val db = Firebase.firestore
        val ref = db.collection("Users").document(publisher)
        
        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                val user : User? = snapshot.toObject(User::class.java)
                usernameText!!.text = user!!.username
                Log.d(TAG, "publishInfo: ${user!!.username}")
                publishername!!.text = user.fullName
                Log.d(TAG, "publishInfo: ${user.fullName}")
                Picasso.get().load(user.profile_photo).into(circleImageHome)

            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

//    private fun publishInfo( circleImageHome: CircleImageView?,usernameText : TextView?) {
//        firebaseAuth = Firebase.auth
//        val db = Firebase.firestore
//        val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
//
//        ref.addSnapshotListener {snapshot, e ->
//            if (e != null) {
//                Log.d(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null && snapshot.exists()) {
//                Log.d(TAG, "Current data: ${snapshot.data}")
//                val user : User ?= snapshot.toObject(User::class.java)
//                usernameText!!.text = user!!.username
//                Picasso.get().load(user!!.profile_photo).placeholder(R.drawable.ic_launcher_background).into(circleImageHome)
//            } else {
//                Log.d(TAG, "Current data: null")
//            } }
//
//    }

   

}