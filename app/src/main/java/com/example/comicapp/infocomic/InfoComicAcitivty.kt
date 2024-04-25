package com.example.comicapp.infocomic

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.databinding.ActivityInfocomicBinding
import com.example.comicapp.item.AllComicAdapter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

private lateinit var binding: ActivityInfocomicBinding

class InfoComicAcitivty : AppCompatActivity() {
    private val storageReference = FirebaseStorage.getInstance().reference
    private lateinit var cht:ChapAdapter
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfocomicBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        openDB = OpenDB(this)
        database = openDB.readableDatabase

        val data = intent.getStringExtra("id_comic")
        val cursor: Cursor = database.rawQuery("select * from comic where id_comic = '$data'",null)
        cursor.use {
            if(cursor.moveToFirst()){
                binding.infoTentruyen.setText(cursor.getString(1)).toString()
                val url = cursor.getString(4)
                if (url != null) {
                    val imageRef = storageReference.child(url)
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        Picasso.get().load(uri).into(binding.infoAnhbia)
                    }.addOnFailureListener { exception ->
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                    }
                }
                binding.infoGt.setText(cursor.getString(2))
            }
        }
        val cursor2:Cursor = database.rawQuery("select name_author from author join comic on author.id_author = comic.id_author where comic.id_comic = '$data'",null)
        cursor2.use {
            if(cursor2.moveToFirst()){
                binding.infoTacgia.setText("Tác Giả: " + cursor2.getString(0).toString())
            }
        }
        val cursor3: Cursor = database.rawQuery("select name_category from category join comic_category on comic_category.id_category = category.id_category join comic on comic.id_comic = comic_category.id_comic where comic.id_comic = '$data'",null)
        cursor3.use {
            var s = ""
            if (cursor3.moveToFirst()){
                s += cursor3.getString(0).toString() + " - "
            }
            while (cursor3.moveToNext())
            {
                s += cursor3.getString(0).toString() + " - "
            }
            binding.infoTheloai.setText("Thể Loại: "+ s +"...")
        }
        val listChap = mutableListOf<Chapter>()
        val cursor4: Cursor = database.rawQuery("select id_chapter,name_chapter,url_chapter from chapter join comic on comic.id_comic = chapter.id_comic where comic.id_comic = '$data'",null)
        cursor4.use {
            if (cursor4.moveToLast()){
                var a = cursor4.getString(0)
                var b = cursor4.getString(1)
                var c = cursor4.getString(2)
                listChap.add(Chapter(a,b,c))
            }
            while (cursor4.moveToPrevious()){
                var a = cursor4.getString(0)
                var b = cursor4.getString(1)
                var c = cursor4.getString(2)
                listChap.add(Chapter(a,b,c))
            }
        }

        val chapter_in_comic = listChap.size

        cht = ChapAdapter(this,listChap)
        binding.listchapter.adapter = cht

        binding.listchapter.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,FinalActivity::class.java)
            intent.putExtra("sochap",chapter_in_comic)
            intent.putExtra("link_chap",listChap[position].url_chapter)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}

