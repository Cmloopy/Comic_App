package com.example.comicapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.databinding.ActivityListallBinding
import com.example.comicapp.infocomic.InfoComicAcitivty
import com.example.comicapp.item.ComicData
import com.example.comicapp.item.FullComicAdapter

private lateinit var binding: ActivityListallBinding

class ListAllActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var full: FullComicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListallBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());

        openDB = OpenDB(this)
        database = openDB.readableDatabase

        val idd = intent.getStringExtra("id")

        val cursor: Cursor = database.rawQuery("SELECT * FROM comic",null)

        val listAll = mutableListOf<ComicData>()

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

        full = FullComicAdapter(this,listAll)
        binding.tatcatruyen.adapter = full
        binding.tatcatruyen.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, InfoComicAcitivty::class.java)
            intent.putExtra("id",idd)
            intent.putExtra("id_comic",listAll[position].id_comic)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}