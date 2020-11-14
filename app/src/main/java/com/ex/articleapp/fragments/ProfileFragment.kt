package com.ex.articleapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ex.articleapp.ProfileEditActvitiy
import com.ex.articleapp.R
import com.ex.articleapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private  var profileBinding: FragmentProfileBinding ?= null
    private val binding  get() = profileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding!!.root





        return view
    }


}