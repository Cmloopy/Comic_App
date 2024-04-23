package com.example.comicapp.fragment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.comicapp.OpenDB
import com.example.comicapp.databinding.FragmentBxhBinding

private lateinit var bbinding: FragmentBxhBinding
private val binding get() = bbinding!!
class BxhFragment : Fragment() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentBxhBinding.inflate(inflater,container,false)

        val data = arguments?.getString("id")

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        return binding.root
    }


}