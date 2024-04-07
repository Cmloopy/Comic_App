package com.example.comicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comicapp.databinding.ActivitySignupBinding

private lateinit var binding: ActivitySignupBinding
class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        binding.buttonDangKi.setOnClickListener {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            val vetrangDN = Intent(this,MainActivity::class.java)
            startActivity(vetrangDN)
        }
        binding.buttonTroVeTrangDN.setOnClickListener {
            val vetrangDN = Intent(this,MainActivity::class.java)
            startActivity(vetrangDN)
        }
    }
}