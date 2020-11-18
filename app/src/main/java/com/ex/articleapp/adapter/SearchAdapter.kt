package com.ex.articleapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.articleapp.R
import com.ex.articleapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(val context : Context , val mUserList : List<User>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var TAG = "SearchAdapter"
    var firebaseUser : FirebaseUser ?= null

     class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         var circle_image_search : CircleImageView ?= null
         var search_fullname : TextView ?= null
         var search_username : TextView  ?= null
         var button_follow : Button?= null

         init {
            circle_image_search =  itemView.findViewById(R.id.circle_image_search)
            search_fullname =  itemView.findViewById(R.id.fullname_text_search)
            search_username =  itemView.findViewById(R.id.username_text_search)
            button_follow =  itemView.findViewById(R.id.button_follow_item)
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_recycler_item_list,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val current_item : User = mUserList[position]

        Picasso.get().load(current_item.profile_photo).placeholder(R.drawable.ic_launcher_background).into(holder.circle_image_search)
        holder.search_username!!.text = current_item.username
        holder.search_fullname!!.text = current_item.fullName

       checkFollowingList(current_item.uid,holder.button_follow)


        //Follow UnFollow
        holder.button_follow!!.setOnClickListener {
            if(holder.button_follow!!.text.toString() == "Follow"){
                firebaseUser = FirebaseAuth.getInstance().currentUser

                val db = Firebase.firestore
                val ref = db.collection("Follow").document(firebaseUser!!.uid)
                val hashmap = hashMapOf(
                        current_item.uid to true
                )

                ref.collection("Following").document(current_item.uid).set(hashmap).addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        val db = Firebase.firestore
                        val reff = db.collection("Follow").document(current_item.uid)

                        val hashmap = hashMapOf(
                                firebaseUser!!.uid to true
                        )

                        reff.collection("Followers").document(firebaseUser!!.uid).set(hashmap).addOnCompleteListener {
                            task ->
                            if(task.isSuccessful){
                                Log.d(TAG, "onBindViewHolder: SuccessFully Followed")
                            }
                        }
                    }else{
                        Log.d(TAG, "onBindViewHolder: Something Went Wrong")
                    }
                }

            }else{
                firebaseUser = FirebaseAuth.getInstance().currentUser
                val db =  Firebase.firestore
                 db.collection("Follow").document(firebaseUser!!.uid).collection("Following")
                        .document(current_item.uid).delete().addOnCompleteListener {
                            task ->
                            if(task.isSuccessful){
                                val db = Firebase.firestore
                                val ref_delete_2 = db.collection("Follow").document(current_item.uid).collection("Followers")
                                        .document(firebaseUser!!.uid).delete().addOnCompleteListener {
                                            task ->
                                            Log.d(TAG, "onBindViewHolder: UnFollow SuccessFul")
                                        }
                            }
                        }
            }
        }


    }

    private fun checkFollowingList(uid: String, buttonFollow: Button?) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val db = Firebase.firestore
        val ref = db.collection("Follow").document(firebaseUser!!.uid).collection("Following").document(uid)


        ref.addSnapshotListener { snapshot, error ->

            if (error != null) {
                Log.w("UserAdapter", "Listen failed.", error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("UserAdapter", "Current data: ${snapshot.data}")
                buttonFollow?.text = "Following"
            } else {
                Log.d("UserAdapter", "Current data: null")
                buttonFollow?.text = "Follow"
            }
        }
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }
}