package com.example.comicapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.comicapp.R
import com.example.comicapp.item.Chapter
import com.example.comicapp.item.ComicData

class ChapAdapter(val activity: Activity, val list: List<Chapter>): ArrayAdapter<ComicData>(activity, R.layout.chap_item) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.chap_item, parent, false)

        val name_chapter = rowView.findViewById<TextView>(R.id.chapp)

        name_chapter.text = list[position].name_chapter

        return rowView
    }
}
