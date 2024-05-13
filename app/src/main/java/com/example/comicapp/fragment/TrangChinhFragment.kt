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
import android.widget.Toast
import com.denzcoskun.imageslider.models.SlideModel
import com.example.comicapp.CategoryActivity
import com.example.comicapp.ListAllActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.R
import com.example.comicapp.SearchActivity
import com.example.comicapp.databinding.FragmentTrangchinhBinding
import com.example.comicapp.infocomic.InfoComicAcitivty
import com.example.comicapp.item.AllComicAdapter
import com.example.comicapp.item.ComicData
import com.example.comicapp.item.RecomendComic

private lateinit var bbinding: FragmentTrangchinhBinding
private val binding get() = bbinding!!

class TrangChinhFragment : Fragment() {
    private lateinit var recomendComic:RecomendComic
    private lateinit var all: AllComicAdapter
    private lateinit var all1: AllComicAdapter
    private lateinit var all2: AllComicAdapter
    private lateinit var all3: AllComicAdapter
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentTrangchinhBinding.inflate(inflater,container,false)

        val data = arguments?.getString("id")

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        val arrlist: ArrayList<SlideModel> = ArrayList()
        arrlist.add(SlideModel(R.drawable.banner1))
        arrlist.add(SlideModel(R.drawable.banner2))
        arrlist.add(SlideModel(R.drawable.banner3))
        arrlist.add(SlideModel(R.drawable.banner4))

        binding.banner.setImageList(arrlist)

        val cursor: Cursor = database.rawQuery("SELECT * FROM comic",null)

        val listAll = mutableListOf<ComicData>()
        val list = mutableListOf<ComicData>()
        val listCN = mutableListOf<ComicData>()
        val listKR = mutableListOf<ComicData>()
        val listJP = mutableListOf<ComicData>()

        cursor.use {
            if(cursor.moveToFirst()){
                var id = cursor.getString(0)
                var name = cursor.getString(1)
                var img = cursor.getString(4)
                listAll.add(ComicData(id,img,name))
            }
            while(cursor.moveToNext()){
                var id = cursor.getString(0)
                var name = cursor.getString(1)
                var img = cursor.getString(4)
                listAll.add(ComicData(id,img,name))
            }
        }

        val (first, second) = randomUniquePair(0, listAll.size-1)
        list.add(listAll[first])
        list.add(listAll[second])

        recomendComic = RecomendComic(requireActivity(),list)
        binding.listtruyen.adapter = recomendComic
        binding.listtruyen.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
            intent.putExtra("id",data)
            intent.putExtra("id_comic",list[position].id_comic)
            startActivity(intent)
        }

        all = AllComicAdapter(requireActivity(),listAll)
        binding.listAlll.adapter = all
        binding.listAlll.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
            intent.putExtra("id",data)
            intent.putExtra("id_comic",listAll[position].id_comic)
            startActivity(intent)
        }

        val cursor2 = database.rawQuery("""select * from comic
            join comic_category on comic.id_comic = comic_category.id_comic
            join category on category.id_category = comic_category.id_category
            where category.name_category = 'manhua' ORDER BY RANDOM() LIMIT 3""",null)

        cursor2.use {
            if(cursor2.moveToFirst()){
                var id = cursor2.getString(0)
                var name = cursor2.getString(1)
                var img = cursor2.getString(4)
                listCN.add(ComicData(id,img,name))
            }
            while(cursor2.moveToNext()){
                var id = cursor2.getString(0)
                var name = cursor2.getString(1)
                var img = cursor2.getString(4)
                listCN.add(ComicData(id,img,name))
            }
        }

        val cursor3 = database.rawQuery("""select * from comic
            join comic_category on comic.id_comic = comic_category.id_comic
            join category on category.id_category = comic_category.id_category
            where category.name_category = 'manwa' ORDER BY RANDOM() LIMIT 3""",null)

        cursor3.use {
            if(cursor3.moveToFirst()){
                var id = cursor3.getString(0)
                var name = cursor3.getString(1)
                var img = cursor3.getString(4)
                listKR.add(ComicData(id,img,name))
            }
            while(cursor3.moveToNext()){
                var id = cursor3.getString(0)
                var name = cursor3.getString(1)
                var img = cursor3.getString(4)
                listKR.add(ComicData(id,img,name))
            }
        }

        val cursor4 = database.rawQuery("""select * from comic
            join comic_category on comic.id_comic = comic_category.id_comic
            join category on category.id_category = comic_category.id_category
            where category.name_category = 'manga' ORDER BY RANDOM() LIMIT 3""",null)

        cursor4.use {
            if(cursor4.moveToFirst()){
                var id = cursor4.getString(0)
                var name = cursor4.getString(1)
                var img = cursor4.getString(4)
                listJP.add(ComicData(id,img,name))
            }
            while(cursor4.moveToNext()){
                var id = cursor4.getString(0)
                var name = cursor4.getString(1)
                var img = cursor4.getString(4)
                listJP.add(ComicData(id,img,name))
            }
        }

        all1 = AllComicAdapter(requireActivity(),listCN)
        binding.comicrcm1.adapter = all1
        binding.comicrcm1.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
            intent.putExtra("id",data)
            intent.putExtra("id_comic",listCN[position].id_comic)
            startActivity(intent)
        }

        all2 = AllComicAdapter(requireActivity(),listKR)
        binding.comicrcm2.adapter = all2
        binding.comicrcm2.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
            intent.putExtra("id",data)
            intent.putExtra("id_comic",listKR[position].id_comic)
            startActivity(intent)
        }

        all3 = AllComicAdapter(requireActivity(),listJP)
        binding.comicrcm3.adapter = all3
        binding.comicrcm3.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
            intent.putExtra("id_comic",listJP[position].id_comic)
            intent.putExtra("id",data)
            startActivity(intent)
        }

        binding.buttonListall.setOnClickListener {
            val intent = Intent(requireContext(), ListAllActivity::class.java)
            intent.putExtra("id",data)
            startActivity(intent)
        }
        binding.search.setOnClickListener {
            val intent = Intent(requireContext(),SearchActivity::class.java)
            intent.putExtra("id",data)
            startActivity(intent)
        }
        binding.category.setOnClickListener {
            val intent = Intent(requireContext(),CategoryActivity::class.java)
            intent.putExtra("id",data)
            startActivity(intent)
        }


        return binding.root
    }

    fun randomUniquePair(min: Int, max: Int): Pair<Int, Int> {
        require(min < max) { "Min must be less than max" }
        val first = (min..max).random()
        var second = (min..max).random()
        while (second == first) {
            second = (min..max).random()
        }
        return Pair(first, second)
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}