package com.example.comicapp

import android.content.ContentValues
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
        database = openDB.writableDatabase

        binding.buttonDangKi.setOnClickListener {
            checkSignUp()
        }
        binding.buttonTroVeTrangDN.setOnClickListener {
            backLogin()
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
                Toast.makeText(this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = ContentValues().apply {
                    put("id_user",usn)
                    put("name_user",name)
                    put("username",usn)
                    put("pass",pw)
                    put("email",mail)
                    put("phone",sdt)
                    put("count",0)
                    put("levels",1)
                }
                val dkk = database.insert("user",null,data)
                cursor.requery()
                if(dkk == -1L){
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show()
                }
                else{
                    showAlertDialog()
                }
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!")
        builder.setMessage("Đăng kí thành công!")
        builder.setPositiveButton("Trở về trang đăng nhập") { dialog, which ->
            backLogin()
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }
    private fun backLogin() {
        val vetrangDN = Intent(this,MainActivity::class.java)
        startActivity(vetrangDN)
        finish()
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}