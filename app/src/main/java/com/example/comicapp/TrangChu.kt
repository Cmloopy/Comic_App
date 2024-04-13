package com.example.comicapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.comicapp.databinding.ActivityTrangchuBinding
import com.example.comicapp.fragment.BxhFragment
import com.example.comicapp.fragment.CanhanFragment
import com.example.comicapp.fragment.TopFragment
import com.example.comicapp.fragment.TrangChinhFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
private lateinit var binding: ActivityTrangchuBinding
private lateinit var bottomNavigationView:BottomNavigationView

class TrangChu: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrangchuBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        val data = intent.getStringExtra("id")
        //Bundle đẩy dữ liệu qua các frag
        val bundle = Bundle()
        bundle.putString("id",data)

        val trangChinhFragment = TrangChinhFragment()
        trangChinhFragment.arguments = bundle
        replaceFragment(trangChinhFragment)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_trangchinh -> {
                    val fragment = TrangChinhFragment()
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    true
                }
                R.id.bottom_bxh -> {
                    val fragment = BxhFragment()
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    true
                }
                R.id.bottom_top -> {
                    val fragment = TopFragment()
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    true
                }
                R.id.bottom_canhan -> {
                    val fragment = CanhanFragment()
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit()
    }
}