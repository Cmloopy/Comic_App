package com.example.comicapp.infocomic

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.TrangChu
import com.example.comicapp.databinding.ActivityFinalBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso

private lateinit var binding: ActivityFinalBinding

class FinalActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var fullHD: LoadImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        openDB = OpenDB(this)
        database = openDB.readableDatabase

        val idd = intent.getStringExtra("id")

        val list_url_chapter = intent.getStringArrayListExtra("list_url")

        val url_chap = intent.getStringExtra("link_chap").toString()

        val storage = Firebase.storage
        val storageRef = storage.reference.child(url_chap)

        val cursor:Cursor = database.rawQuery("select id_chapter,name_chapter from chapter where url_chapter = '$url_chap'",null)
        cursor.use {
            if(cursor.moveToFirst()){
                binding.textChapp.setText(cursor.getString(1).toString())
            }
        }

        val fileNames = mutableListOf<String>()
        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    fileNames.add(url_chap + "/" + item.name)
                }

                fullHD = LoadImageAdapter(this,fileNames)
                binding.allcomicimage.adapter = fullHD
            }
            .addOnFailureListener { exception ->

            }

        val updateCount = database.rawQuery("select * from user where id_user = '$idd'",null)
        updateCount.use {
            if (updateCount.moveToFirst()){
                val c = updateCount.getInt(6) + 1
                val dataupdate = ContentValues().apply {
                    put("count",c)
                }
                database.update("user",dataupdate,"id_user = ?", arrayOf(updateCount.getString(0)))
            }
        }
        val updateView = database.rawQuery("select * from comic join chapter on comic.id_comic = chapter.id_comic where chapter.url_chapter = '$url_chap'",null)
        updateView.use {
            if (updateView.moveToFirst()){
                val v = updateView.getInt(3) + 1
                val dataupdate = ContentValues().apply {
                    put("view",v)
                }
                database.update("comic",dataupdate,"id_comic = ?", arrayOf(updateView.getString(0)))
                updateView.requery()
            }
        }

        val sc = list_url_chapter!!.size
        binding.buttonNext.setOnClickListener {
            val i = list_url_chapter!!.indexOf(url_chap)
            if(i < sc-1){
                val intent = Intent(this,FinalActivity::class.java)
                intent.putExtra("link_chap",list_url_chapter[i+1])
                intent.putStringArrayListExtra("list_url",list_url_chapter)
                startActivity(intent)
                finish()
            }
            else{
                showAlertDialog1()
            }
        }
        binding.buttonPre.setOnClickListener {
            val i = list_url_chapter!!.indexOf(url_chap)
            if(i > 0){
                val intent = Intent(this,FinalActivity::class.java)
                intent.putExtra("link_chap",list_url_chapter[i-1])
                intent.putStringArrayListExtra("list_url",list_url_chapter)
                intent.putExtra("id",idd)
                startActivity(intent)
                finish()
            }
            else{
                showAlertDialog2()
            }
        }
        binding.buttonBackhome.setOnClickListener {
            val intent = Intent(this,TrangChu::class.java)
            intent.putExtra("id",idd)
            startActivity(intent)
        }
    }

    private fun showAlertDialog1() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!!")
        builder.setMessage("Hết Chap mới ròii =.=")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun showAlertDialog2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!!")
        builder.setMessage("Đây là Chap đầu tiên =.=")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}