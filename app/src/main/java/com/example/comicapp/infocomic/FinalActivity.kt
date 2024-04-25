package com.example.comicapp.infocomic

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comicapp.OpenDB
import com.example.comicapp.R
import com.example.comicapp.databinding.ActivityFinalBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
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

        val sochap = intent.getStringExtra("sochap")
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
                /*val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)
                binding.allimagecomic.adapter = adapter*/
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }

    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}