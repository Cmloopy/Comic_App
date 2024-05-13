package com.example.comicapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comicapp.databinding.ActivitySearchBinding
import com.example.comicapp.infocomic.InfoComicAcitivty
import com.example.comicapp.item.ComicData
import com.example.comicapp.item.FullComicAdapter

private lateinit var binding: ActivitySearchBinding
class SearchActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var data_find: FullComicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openDB = OpenDB(this)
        database = openDB.readableDatabase

        val idd = intent.getStringExtra("id")

        val listAllCM = mutableListOf<String>()

        val cursor: Cursor = database.rawQuery("select name_comic from comic",null)
        cursor.use {
            if(cursor.moveToFirst()){
                listAllCM.add(cursor.getString(0).toString())
            }
            while (cursor.moveToNext()){
                listAllCM.add(cursor.getString(0).toString())
            }
        }
        val adt = ArrayAdapter(this, android.R.layout.simple_list_item_1,listAllCM)
        binding.autoCompleteTen.setAdapter(adt)

        binding.autoCompleteTen.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val textt = binding.autoCompleteTen.text.toString()
                val cm_find = mutableListOf<ComicData>()
                val cursor = database.rawQuery("select id_comic, image_url, name_comic from comic where name_comic = '$textt'",null)
                if (cursor.moveToFirst()){
                    val a = cursor.getString(0)
                    val b = cursor.getString(1)
                    val c = cursor.getString(2)
                    cm_find.add(ComicData(a,b,c))
                }
                data_find = FullComicAdapter(this,cm_find)
                binding.listFind.adapter = data_find

                binding.listFind.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this, InfoComicAcitivty::class.java)
                    intent.putExtra("id_comic",cm_find[position].id_comic)
                    intent.putExtra("id",idd)
                    startActivity(intent)
                }
            }
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}