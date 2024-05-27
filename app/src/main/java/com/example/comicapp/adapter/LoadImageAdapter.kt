package com.example.comicapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.comicapp.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class LoadImageAdapter(val activity: Activity, val list: List<String>): ArrayAdapter<String>(activity, R.layout.anh_truyen) {
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowView = contexts.inflate(R.layout.anh_truyen, parent, false)

        val imgcomicc = rowView.findViewById<ImageView>(R.id.image_comiccccc)

        val imagePath = list[position]

        if (imagePath != null) {
            val imageRef = storageReference.child(imagePath)
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(imgcomicc)
            }.addOnFailureListener { exception ->
                // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
            }
        }

        return rowView
    }
}