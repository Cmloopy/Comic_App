package com.example.comicapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.comicapp.R
import com.example.comicapp.item.ComicData
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class RecomendComic(val context: Activity, val list: List<ComicData>): ArrayAdapter<ComicData>(context, R.layout.list_comic) {
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getCount(): Int {
        return 2
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = context.layoutInflater
        val rowView = contexts.inflate(R.layout.list_comic,parent,false)

        val img = rowView.findViewById<ImageView>(R.id.anh_bia)
        val name = rowView.findViewById<TextView>(R.id.name_comic)


        name.text = list[position].name_comic
        val imagePath = list[position].url
        if (imagePath != null) {
            val imageRef = storageReference.child(imagePath)
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(img)
            }.addOnFailureListener { exception ->
                // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
            }
        }

        return rowView
    }
}