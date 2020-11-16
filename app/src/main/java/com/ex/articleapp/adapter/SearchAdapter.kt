package com.ex.articleapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.articleapp.R
import com.ex.articleapp.data.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(val context : Context , val mUserList : List<User>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

     class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         var circle_image_search : CircleImageView ?= null
         var search_fullname : TextView ?= null
         var search_username : TextView  ?= null

         init {
            circle_image_search =  itemView.findViewById(R.id.circle_image_search)
            search_fullname =  itemView.findViewById(R.id.fullname_text_search)
            search_username =  itemView.findViewById(R.id.username_text_search)
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

    }

    override fun getItemCount(): Int {
        return mUserList.size
    }
}