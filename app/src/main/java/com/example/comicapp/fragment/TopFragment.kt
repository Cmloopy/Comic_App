package com.example.comicapp.fragment

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.comicapp.OpenDB
import com.example.comicapp.R
import com.example.comicapp.databinding.FragmentTopBinding
import com.example.comicapp.item.TopAdapter
import com.example.comicapp.item.TopData

private lateinit var bbinding: FragmentTopBinding
private val binding get() = bbinding!!

class TopFragment : Fragment() {
    private lateinit var topAdapter: TopAdapter
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentTopBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        val data = arguments?.getString("id")

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        var cursor: Cursor = database.rawQuery("SELECT * FROM user ORDER BY count DESC LIMIT 9",null)

        val list = mutableListOf<TopData>()
        val listIcon = mutableListOf<Int>()

        listIcon.add(R.drawable.gold)
        listIcon.add(R.drawable.silver)
        listIcon.add(R.drawable.bronze)
        listIcon.add(R.drawable.four)
        listIcon.add(R.drawable.five)
        listIcon.add(R.drawable.six)
        listIcon.add(R.drawable.seven)
        listIcon.add(R.drawable.eight)
        listIcon.add(R.drawable.nine)

        var cnt = 0

        cursor.use {
            if (cursor.moveToFirst()){
                var name:String = cursor.getString(1)
                var count: String = cursor.getInt(6).toString()
                var level: String = cursor.getInt(7).toString()
                list.add(TopData(listIcon[cnt],name,level,count))
                cnt++
            }
            while (cursor.moveToNext()){
                var name:String = cursor.getString(1)
                var count: String = cursor.getInt(6).toString()
                var level: String = cursor.getInt(7).toString()
                list.add(TopData(listIcon[cnt],name,level,count))
                cnt++
            }
        }

        topAdapter = TopAdapter(requireActivity(),list)
        binding.topdoctruyen.adapter = topAdapter
        return binding.root
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

}