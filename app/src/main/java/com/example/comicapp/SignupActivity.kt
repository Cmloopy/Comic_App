package com.example.comicapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comicapp.databinding.ActivitySignupBinding

private lateinit var binding: ActivitySignupBinding
class SignupActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());

        openDB = OpenDB(this)
        database = openDB.readableDatabase

        binding.buttonDangKi.setOnClickListener {
            checkSignUp()
        }
        binding.buttonTroVeTrangDN.setOnClickListener {
            val vetrangDN = Intent(this,MainActivity::class.java)
            startActivity(vetrangDN)
        }
    }

    private fun checkSignUp() {
        var usn = binding.editTextTaiKhoanDangKi.text.toString()
        var pw = binding.editTextPassword.text.toString()
        var mail = binding.editTextEmailAddress.text.toString()
        var sdt = binding.editTextPhone.text.toString()
        var name = binding.editTextTenHT.text.toString()

        var cursor = database.rawQuery("SELECT * FROM user WHERE username = '$usn'",null)
        cursor.use {
            if (cursor.moveToFirst()){
                showAlertDialog()
            }
            else{
                
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!")
        builder.setMessage("Tên tài khoản đã tồn tại! Hãy sử dụng tên tài khoản khác.")
        builder.setPositiveButton("OK") { dialog, which ->
            binding.editTextTaiKhoanDangKi.setText("")
            binding.editTextPassword.setText("")
            binding.editTextEmailAddress.setText("")
            binding.editTextPhone.setText("")
            binding.editTextTenHT.setText("")
            dialog.dismiss()
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }
}