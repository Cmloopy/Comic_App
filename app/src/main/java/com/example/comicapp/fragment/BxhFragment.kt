package com.example.comicapp.fragment

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.comicapp.OpenDB
import com.example.comicapp.R
import com.example.comicapp.databinding.FragmentBxhBinding
import com.example.comicapp.infocomic.InfoComicAcitivty
import com.example.comicapp.item.BxhAdapter
import com.example.comicapp.item.BxhItemData
import com.example.comicapp.item.TopData

private lateinit var bbinding: FragmentBxhBinding
private val binding get() = bbinding!!
class BxhFragment : Fragment() {
    private lateinit var bxhAdapter: BxhAdapter
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

        val list = mutableListOf<BxhItemData>()
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

        val cursor: Cursor = database.rawQuery("select comic.id_comic, comic.name_comic, comic.'view', comic.image_url from comic order by comic.'view' desc limit 9",null)

        cursor.use {
            if (cursor.moveToFirst()){
                var id = cursor.getString(0).toString()
                var urll = cursor.getString(3).toString()
                var name = cursor.getString(1).toString()
                var count = cursor.getInt(2).toString()
                list.add(BxhItemData(listIcon[cnt],id,urll,name,count))
                cnt++
            }
            while (cursor.moveToNext()){
                var id = cursor.getString(0).toString()
                var urll = cursor.getString(3).toString()
                var name = cursor.getString(1).toString()
                var count = cursor.getInt(2).toString()
                list.add(BxhItemData(listIcon[cnt],id,urll,name,count))
                cnt++
            }
        }

        bxhAdapter = BxhAdapter(requireActivity(),list)
        binding.listBxhh.adapter = bxhAdapter

        binding.listBxhh.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), InfoComicAcitivty::class.java)
            intent.putExtra("id_comic",list[position].id_comic)
            intent.putExtra("id",data)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

}