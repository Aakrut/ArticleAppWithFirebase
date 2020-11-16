package com.ex.articleapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ex.articleapp.R
import com.ex.articleapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var searchBinding: FragmentSearchBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        val view = searchBinding.root



        return view

    }


}