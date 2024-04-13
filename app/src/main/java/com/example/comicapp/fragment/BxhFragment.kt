package com.example.comicapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.comicapp.R


class BxhFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewBXH:View = inflater.inflate(R.layout.fragment_bxh, container, false)
        // Inflate the layout for this fragment
        val data = arguments?.getString("id")
        return viewBXH
    }


}