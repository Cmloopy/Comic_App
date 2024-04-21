package com.example.comicapp.item

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.comicapp.R
import com.google.firebase.storage.FirebaseStorage

class RecomendComic(val activity: Activity, val list: List<ComicData>): ArrayAdapter<ComicData>(activity, R.layout.list_comic) {
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getCount(): Int {
        return 2
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.list_comic,parent,false)

        val img = rowView.findViewById<ImageView>(R.id.anh_bia)
        val name = rowView.findViewById<TextView>(R.id.name_comic)


        name.text = list[position].name_comic
        val imagePath = list[position].url
        if (imagePath != null) {
            val imageRef = storageReference.child(imagePath)
            Glide.with(activity)
                .load(imageRef)
                .into(img)
        }

        return rowView
    }
}