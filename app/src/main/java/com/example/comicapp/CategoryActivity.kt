package com.example.comicapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.databinding.ActivityCategoryBinding
import com.example.comicapp.adapter.InfoComicAcitivty
import com.example.comicapp.item.ComicData
import com.example.comicapp.adapter.FullComicAdapter
import com.example.comicapp.dtbase.OpenDB

private lateinit var binding: ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var checkCTG: FullComicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        openDB = OpenDB(this)
        database = openDB.readableDatabase

        val idd = intent.getStringExtra("id")

        val listCTG = mutableListOf<String>()
        listCTG.add("")

        val cursor: Cursor = database.rawQuery("select name_category from category",null)
        cursor.use {
            if (cursor.moveToFirst()){
                var s = cursor.getString(0).toString()
                listCTG.add(s)
            }
            while(cursor.moveToNext()){
                var s = cursor.getString(0).toString()
                listCTG.add(s)
            }
        }

        var scr = ArrayAdapter(this,android.R.layout.simple_spinner_item, listCTG)
        binding.spinnerCategory.adapter = scr

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val choose = listCTG[position]
                val listtttt = mutableListOf<ComicData>()
                if(choose != "") {
                    val cursor2: Cursor = database.rawQuery(
                        "select comic.id_comic, comic.name_comic, comic.image_url from comic join comic_category on comic_category.id_comic = comic.id_comic join category on category.id_category = comic_category.id_category where category.name_category = '$choose'",
                        null
                    )
                    cursor2.use {
                        if (cursor2.moveToFirst()) {
                            var a = cursor2.getString(0).toString()
                            var b = cursor2.getString(2).toString()
                            var c = cursor2.getString(1).toString()
                            listtttt.add(ComicData(a, b, c))
                        }
                        while (cursor2.moveToNext()) {
                            var a = cursor2.getString(0).toString()
                            var b = cursor2.getString(2).toString()
                            var c = cursor2.getString(1).toString()
                            listtttt.add(ComicData(a, b, c))
                        }
                    }
                }
                checkCTG = FullComicAdapter(this@CategoryActivity,listtttt)
                binding.comicSearchCategory.adapter = checkCTG

                binding.comicSearchCategory.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@CategoryActivity, InfoComicAcitivty::class.java)
                    intent.putExtra("id_comic",listtttt[position].id_comic)
                    intent.putExtra("id",idd)
                    startActivity(intent)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.backkk.setOnClickListener {

        }
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}