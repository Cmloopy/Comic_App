package com.example.comicapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.comicapp.databinding.ActivityChangeBinding

private lateinit var binding: ActivityChangeBinding
class ChangeActivity : AppCompatActivity() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openDB = OpenDB(this)
        database = openDB.writableDatabase

        id = intent.getStringExtra("id").toString()

        binding.buttonDoiTen.setOnClickListener {
            DoiTen()
        }

        binding.buttonDMKKK.setOnClickListener {
            DoiMatKhau()
        }
    }

    private fun DoiTen(){
        val newName = binding.editTextnewten.text.toString()
        var cursor = database.rawQuery("select * from user where id_user = '$id'",null)
        cursor.use {
            if (cursor.moveToFirst()){
                val dataa = ContentValues().apply {
                    put("name_user",newName)
                }
                database.update("user",dataa,"id_user = ?", arrayOf(cursor.getString(0)))
                cursor.requery()
                alertDT()
            }
        }
    }

    private fun DoiMatKhau(){
        val oldPass = binding.editTextmkcu.text.toString()
        val newPass = binding.editTextmkmoi.text.toString()
        val newPassAgain = binding.editTextnhaplai.text.toString()
        if(newPass == newPassAgain && newPass != ""){
            val cursor = database.rawQuery("select * from user where id_user = '$id'",null)
            cursor.use {
                if(cursor.moveToFirst()){
                    if(oldPass == cursor.getString(3).toString()){
                        val datachage = ContentValues().apply {
                            put("pass", newPass)
                        }
                        database.update("user",datachage,"id_user = ?", arrayOf(cursor.getString(0)))
                        cursor.requery()
                        alertDMK()
                        binding.textThongBao.setText("")
                    }
                    else{
                        binding.textThongBao.setText("Sai mật khẩu cũ!")
                        binding.textThongBao.setTextColor(Color.RED)
                        binding.editTextmkcu.setText("")
                    }
                }
            }
        }
        else if (newPass != newPassAgain){
            binding.textThongBao.setText("Sai mật khẩu nhập lại!")
            binding.textThongBao.setTextColor(Color.RED)
            binding.editTextnewten.setText("")
            binding.editTextnhaplai.setText("")
        }
        else {
            binding.textThongBao.setText("Mật khẩu mới không được phép trống!")
            binding.textThongBao.setTextColor(Color.RED)
        }
    }

    private fun alertDT() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!!")
        builder.setMessage("Đổi tên thành công!")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun alertDMK() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông Báo!!")
        builder.setMessage("Đổi mật khẩu thành công!")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        // Tạo và hiển thị AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
        finish()
    }
}