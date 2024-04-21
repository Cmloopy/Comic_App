package com.example.comicapp.item

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.comicapp.R
import com.google.firebase.storage.FirebaseStorage

class AllComicAdapter (val activity: Activity, val list: List<ComicData>): ArrayAdapter<ComicData>(activity, R.layout.listall_comic) {
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getCount(): Int {
        return 3
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.listall_comic,parent,false)

        val imgg = rowView.findViewById<ImageView>(R.id.bia)
        val tenn = rowView.findViewById<TextView>(R.id.tenn)

        tenn.text = list[position].name_comic

        return rowView
    }
}