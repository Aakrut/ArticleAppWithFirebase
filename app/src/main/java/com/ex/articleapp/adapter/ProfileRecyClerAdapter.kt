package com.ex.articleapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.articleapp.R
import com.ex.articleapp.data.Article
import com.ex.articleapp.databinding.SearchRecyclerItemListBinding
import org.w3c.dom.Text

class ProfileRecyClerAdapter(val context: Context , val article : List<Article>) : RecyclerView.Adapter<ProfileRecyClerAdapter.ViewHolder> () {


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val text_title : TextView
        val text_about : TextView

        init {

            text_title = itemView.findViewById(R.id.article_item_title)
            text_about = itemView.findViewById(R.id.article_item_about)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_item_profile,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article_item : Article = article[position]

        holder.text_about.text = article_item.about
        holder.text_title.text = article_item.title

    }

    override fun getItemCount(): Int {
        return article.size
    }
}
















