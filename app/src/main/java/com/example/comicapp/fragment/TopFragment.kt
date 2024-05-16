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
                var level: String = level(cursor.getInt(6))
                list.add(TopData(listIcon[cnt],name,level,count))
                cnt++
            }
            while (cursor.moveToNext()){
                var name:String = cursor.getString(1)
                var count: String = cursor.getInt(6).toString()
                var level: String = level(cursor.getInt(6))
                list.add(TopData(listIcon[cnt],name,level,count))
                cnt++
            }
        }

        topAdapter = TopAdapter(requireActivity(),list)
        binding.topdoctruyen.adapter = topAdapter
        return binding.root
    }

    private fun level(a: Int): String{
        val x = when(a) {
            in 0..9 -> 1
            in 10..99 -> 2
            in 100..999 -> 3
            in 1000..9999 -> 4
            in 10000..99999 -> 5
            in 100000..999999 -> 6
            else -> 7
        }
        return Integer.toString(x)
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

}