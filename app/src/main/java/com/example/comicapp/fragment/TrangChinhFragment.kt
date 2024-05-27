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
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.comicapp.CategoryActivity
import com.example.comicapp.ListAllActivity
import com.example.comicapp.dtbase.OpenDB
import com.example.comicapp.R
import com.example.comicapp.SearchActivity
import com.example.comicapp.databinding.FragmentTrangchinhBinding
import com.example.comicapp.adapter.InfoComicAcitivty
import com.example.comicapp.adapter.AllComicAdapter
import com.example.comicapp.item.ComicData
import com.example.comicapp.adapter.RecomendComic

private lateinit var bbinding: FragmentTrangchinhBinding
private val binding get() = bbinding!!

class TrangChinhFragment : Fragment() {
    private lateinit var recomendComic: RecomendComic
    private lateinit var all: AllComicAdapter
    private lateinit var all1: AllComicAdapter
    private lateinit var all2: AllComicAdapter
    private lateinit var all3: AllComicAdapter
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var dataa: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentTrangchinhBinding.inflate(inflater,container,false)

        val data = arguments?.getString("id")

        dataa = data.toString()

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        val arrlist: ArrayList<SlideModel> = ArrayList()
        arrlist.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        arrlist.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        arrlist.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        arrlist.add(SlideModel(R.drawable.banner4, ScaleTypes.FIT))

        binding.banner.setImageList(arrlist)

        binding.banner.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                when (position) {
                    0 -> nextActivity("cm25",dataa)
                    1 -> nextActivity("cm12",dataa)
                    2 -> nextActivity("cm02",dataa)
                    3 -> nextActivity("cm26",dataa)
                }
            }
        })


        val cursor: Cursor = database.rawQuery("SELECT * FROM comic",null)

        val listAll = mutableListOf<ComicData>()
        val list = mutableListOf<ComicData>()
        val listCN = mutableListOf<ComicData>()
        val listKR = mutableListOf<ComicData>()
        val listJP = mutableListOf<ComicData>()

        cursor.use {
            if(cursor.moveToFirst()){
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val img = cursor.getString(4)
                listAll.add(ComicData(id,img,name))
            }
            while(cursor.moveToNext()){
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val img = cursor.getString(4)
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
                val id = cursor2.getString(0)
                val name = cursor2.getString(1)
                val img = cursor2.getString(4)
                listCN.add(ComicData(id,img,name))
            }
            while(cursor2.moveToNext()){
                val id = cursor2.getString(0)
                val name = cursor2.getString(1)
                val img = cursor2.getString(4)
                listCN.add(ComicData(id,img,name))
            }
        }

        val cursor3 = database.rawQuery("""select * from comic
            join comic_category on comic.id_comic = comic_category.id_comic
            join category on category.id_category = comic_category.id_category
            where category.name_category = 'manwa' ORDER BY RANDOM() LIMIT 3""",null)

        cursor3.use {
            if(cursor3.moveToFirst()){
                val id = cursor3.getString(0)
                val name = cursor3.getString(1)
                val img = cursor3.getString(4)
                listKR.add(ComicData(id,img,name))
            }
            while(cursor3.moveToNext()){
                val id = cursor3.getString(0)
                val name = cursor3.getString(1)
                val img = cursor3.getString(4)
                listKR.add(ComicData(id,img,name))
            }
        }

        val cursor4 = database.rawQuery("""select * from comic
            join comic_category on comic.id_comic = comic_category.id_comic
            join category on category.id_category = comic_category.id_category
            where category.name_category = 'manga' ORDER BY RANDOM() LIMIT 3""",null)

        cursor4.use {
            if(cursor4.moveToFirst()){
                val id = cursor4.getString(0)
                val name = cursor4.getString(1)
                val img = cursor4.getString(4)
                listJP.add(ComicData(id,img,name))
            }
            while(cursor4.moveToNext()){
                val id = cursor4.getString(0)
                val name = cursor4.getString(1)
                val img = cursor4.getString(4)
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

    private fun nextActivity(s: String, x:String) {
        val intent = Intent(requireContext(),InfoComicAcitivty::class.java)
        intent.putExtra("id_comic",s)
        intent.putExtra("id",x)
        startActivity(intent)

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