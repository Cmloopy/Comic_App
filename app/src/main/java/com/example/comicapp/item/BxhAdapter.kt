package com.example.comicapp.item

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.comicapp.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class BxhAdapter(val activity: Activity, val list: List<BxhItemData>): ArrayAdapter<BxhItemData>(activity, R.layout.bxh_item) {
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.bxh_item,parent,false)

        val xh = rowView.findViewById<ImageView>(R.id.top_bxhhh)
        val imgg = rowView.findViewById<ImageView>(R.id.biabxh)
        val tenn = rowView.findViewById<TextView>(R.id.tentruyen_bxh)
        val lx = rowView.findViewById<TextView>(R.id.luotxem_bxh)

        val imagePath = list[position].url
        if (imagePath != null) {
            val imageRef = storageReference.child(imagePath)
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(imgg)
            }.addOnFailureListener { exception ->
                // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
            }
        }
        xh.setImageResource(list[position].icon)
        tenn.text = list[position].name_comic
        lx.text = list[position].countt

        return rowView
    }
}