package com.example.comicapp.infocomic

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.R
import com.example.comicapp.databinding.ActivityInfocomicBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private lateinit var binding: ActivityInfocomicBinding

class InfoComicAcitivty : AppCompatActivity() {
    private val storageReference = FirebaseStorage.getInstance().reference
    private lateinit var cht:ChapAdapter
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var idd1 : String
    private lateinit var data1 : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfocomicBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        openDB = OpenDB(this)
        database = openDB.writableDatabase

        val data = intent.getStringExtra("id_comic")
        val idd = intent.getStringExtra("id")
        idd1 = idd.toString()
        data1 = data.toString()
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
        val list_url = mutableListOf<String>()
        val listChap = mutableListOf<Chapter>()
        val cursor4: Cursor = database.rawQuery("select id_chapter,name_chapter,url_chapter from chapter join comic on comic.id_comic = chapter.id_comic where comic.id_comic = '$data'",null)
        cursor4.use {
            if (cursor4.moveToLast()){
                var a = cursor4.getString(0)
                var b = cursor4.getString(1)
                var c = cursor4.getString(2)
                listChap.add(Chapter(a,b,c))
                list_url.add(c)
            }
            while (cursor4.moveToPrevious()){
                var a = cursor4.getString(0)
                var b = cursor4.getString(1)
                var c = cursor4.getString(2)
                listChap.add(Chapter(a,b,c))
                list_url.add(c)
            }
        }

        val list_url_chapter: ArrayList<String> = ArrayList(list_url)
        list_url_chapter.reverse()

        cht = ChapAdapter(this,listChap)
        binding.listchapter.adapter = cht

        checkTD()

        binding.listchapter.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,FinalActivity::class.java)
            intent.putStringArrayListExtra("list_url",list_url_chapter)
            intent.putExtra("link_chap",listChap[position].url_chapter)
            intent.putExtra("id",idd)
            startActivity(intent)
        }
        binding.buttonDTD.setOnClickListener {
            val intent = Intent(this, FinalActivity::class.java)
            intent.putStringArrayListExtra("list_url", list_url_chapter)
            intent.putExtra("link_chap", listChap[listChap.size - 1].url_chapter)
            intent.putExtra("id", idd)
            startActivity(intent)
        }
        val tttd  = binding.buttonTheoDoi.text.toString()
        binding.buttonTheoDoi.setOnClickListener {
            if(tttd == "Theo dõi"){
                val datatd = ContentValues().apply {
                    put("id_user",idd)
                    put("id_comic",data)
                    put("fl_time",getCurrentDate())
                }
                database.insert("follow",null,datatd)
                checkTD()
            }
            else{
                val cursorCheck = database.rawQuery("select * from follow where id_user = '$idd' and id_comic = '$data'",null)
                cursorCheck.use {
                    if(cursorCheck.moveToFirst()){
                        database.delete("follow","id_fl = ?", arrayOf(cursorCheck.getString(0)))
                        checkTD()
                    }
                }
            }
        }
    }

    private fun checkTD(){
        val cursorCheck = database.rawQuery("select * from follow where id_user = '$idd1' and id_comic = '$data1'",null)
        if(cursorCheck.moveToFirst()){
            binding.buttonTheoDoi.setText("Bỏ theo dõi")
            binding.buttonTheoDoi.setBackgroundColor(Color.GRAY)
        }
        else{
            binding.buttonTheoDoi.setText("Theo dõi")
            val color = resources.getColor(R.color.lavender, theme)
            binding.buttonTheoDoi.setBackgroundColor(color)
        }
        cursorCheck.requery()
    }

    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
        return currentDate.format(formatter)
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}

