package com.example.comicapp.item

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.comicapp.R

class TopAdapter(val activity: Activity, val list: List<TopData>):ArrayAdapter<TopData>(activity, R.layout.top_item) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.top_item,parent,false)

        val icon = rowView.findViewById<ImageView>(R.id.stt)
        val tennd = rowView.findViewById<TextView>(R.id.tennd)
        val capdo = rowView.findViewById<TextView>(R.id.capdo)
        val sochap = rowView.findViewById<TextView>(R.id.sochap)

        icon.setImageResource(list[position].icon)
        tennd.text = list[position].name
        capdo.text = list[position].level
        sochap.text = list[position].count

        return rowView
    }
}