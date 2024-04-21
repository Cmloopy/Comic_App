package com.example.comicapp.fragment

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.comicapp.MainActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.databinding.FragmentCanhanBinding

private lateinit var bbinding: FragmentCanhanBinding
private val binding get() = bbinding!!
class CanhanFragment : Fragment() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentCanhanBinding.inflate(inflater,container,false)
        val data = arguments?.getString("id")

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        var cursor: Cursor = database.rawQuery("SELECT * FROM user WHERE username = '$data'",null)
        cursor.use {
            if(cursor.moveToFirst()){
                binding.nameuser.text = cursor.getString(1)
                binding.textViewSoChap.text = cursor.getString(6)
                binding.textViewCapDo.text = cursor.getString(7)
            }
        }

        binding.buttonDMK.setOnClickListener {
            changePasswordDialog()
        }

        binding.buttonDangXuat.setOnClickListener {
            showAlertDialog()
        }
        return binding.root
    }

    private fun changePasswordDialog() {
        
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ooooooo...")
        builder.setMessage("Bạn chắc chắn muốn đăng xuất?")
        builder.setPositiveButton("Không...") { dialog, which ->
            dialog.dismiss() // Đóng AlertDialog
        }
        builder.setNegativeButton("Đúng rồi!") { dialog, which ->
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}