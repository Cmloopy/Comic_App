package com.example.comicapp.infocomic

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.databinding.ActivityInfocomicBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

private lateinit var binding: ActivityInfocomicBinding

class InfoComicAcitivty : AppCompatActivity() {
    private val storageReference = FirebaseStorage.getInstance().reference

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
        var chap_num:Int = 0
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
                chap_num = cursor.getInt(6)
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
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}

