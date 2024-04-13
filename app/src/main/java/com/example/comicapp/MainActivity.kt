package com.example.comicapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        openDB = OpenDB(this)
        database = openDB.readableDatabase // hoặc openDB.writableDatabase
        binding.buttonDangNhap.setOnClickListener {
            if(checkDangNhap()){
                val userid = binding.editTextTaiKhoan.text.toString()
                val intent = Intent(this,TrangChu::class.java)
                intent.putExtra("id",userid)
                database.close()
                startActivity(intent)
            }
            else
            {
                showAlertDialog()
                binding.editTextTaiKhoan.setText("")
                binding.editTextMatKhau.setText("")
            }
        }
        binding.buttonChuyenTrangDK.setOnClickListener {
            val ctdk = Intent(this,SignupActivity::class.java)
            database.close()
            startActivity(ctdk)
        }
    }

    private fun checkDangNhap(): Boolean {
        val tk = binding.editTextTaiKhoan.text.toString()
        val mk = binding.editTextMatKhau.text.toString()
        val cursor: Cursor = database.rawQuery("SELECT * FROM user WHERE username = '$tk' AND pass = '$mk'",null)
        cursor.use {
            if(cursor.moveToFirst()){
                return true
            }
        }
        return false
    }
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oops!! Lỗi rồi >.<")
        builder.setMessage("Sai tài khoản hoặc mật khẩu rồi kìa!")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }
}