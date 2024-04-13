package com.example.comicapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.comicapp.R

class TopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewTop:View = inflater.inflate(R.layout.fragment_top, container, false)
        // Inflate the layout for this fragment
        val data = arguments?.getString("id")
        return viewTop
    }


}