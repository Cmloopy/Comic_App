package com.example.comicapp.fragment

import android.icu.number.Scale
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.comicapp.R

class TrangChinhFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.fragment_trangchinh, container, false)

        var imageSlider: ImageSlider = view.findViewById(R.id.banner)
        var arrlist: ArrayList<SlideModel> = ArrayList()
        arrlist.add(SlideModel(R.drawable.banner1))
        arrlist.add(SlideModel(R.drawable.banner2))
        arrlist.add(SlideModel(R.drawable.banner3))
        arrlist.add(SlideModel(R.drawable.banner4))

        imageSlider.setImageList(arrlist)

        return view
    }


}