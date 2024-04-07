package com.example.comicapp

import android.os.Bundle
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
        replaceFragment(TrangChinhFragment())
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_trangchinh -> {
                    replaceFragment(TrangChinhFragment())
                    true
                }
                R.id.bottom_bxh -> {
                    replaceFragment(BxhFragment())
                    true
                }
                R.id.bottom_top -> {
                    replaceFragment(TopFragment())
                    true
                }
                R.id.bottom_canhan -> {
                    replaceFragment(CanhanFragment())
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