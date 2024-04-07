package com.example.comicapp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.withContext
import com.example.comicapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        binding.buttonDangNhap.setOnClickListener {
            var tk =  binding.editTextTaiKhoan.text.toString()
            var mk = binding.editTextMatKhau.text.toString()
            if(tk == "admin" && mk == "admin"){
                var trangchinh = Intent(this, TrangChu::class.java)
                startActivity(trangchinh)
            }
            else {
                binding.editTextMatKhau.setText("")
                binding.editTextTaiKhoan.setText("")
                Toast.makeText(this, "Sai tkmk", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonChuyenTrangDK.setOnClickListener {
            var ctdk = Intent(this,SignupActivity::class.java)
            startActivity(ctdk)
        }
    }
}