package com.example.comicapp.fragment

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.comicapp.MainActivity
import com.example.comicapp.OpenDB
import com.example.comicapp.databinding.FragmentCanhanBinding
import com.example.comicapp.infocomic.InfoComicAcitivty
import com.example.comicapp.item.ComicData
import com.example.comicapp.item.FollowAdapter

private lateinit var bbinding: FragmentCanhanBinding
private val binding get() = bbinding!!
class CanhanFragment : Fragment() {
    private lateinit var openDB: OpenDB
    private lateinit var database: SQLiteDatabase
    private lateinit var followAdapter: FollowAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bbinding = FragmentCanhanBinding.inflate(inflater,container,false)
        val data = arguments?.getString("id")

        openDB = OpenDB(requireContext())
        database = openDB.readableDatabase

        val cursor: Cursor = database.rawQuery("SELECT * FROM user WHERE username = '$data'",null)
        cursor.use {
            if(cursor.moveToFirst()){
                val s = cursor.getString(6)
                binding.nameuser.text = cursor.getString(1)
                binding.textViewSoChap.text = s
                binding.textViewCapDo.text = level(Integer.parseInt(s))
            }
        }

        val listTD = mutableListOf<ComicData>()

        val cursor2 = database.rawQuery("""select comic.id_comic, comic.image_url, comic.name_comic from comic join follow on follow.id_comic = comic.id_comic join user on follow.id_user = user.id_user where user.id_user = '$data' order by follow.fl_time DESC""",null)
        cursor2.use {
            if (cursor2.moveToFirst()){
                val a = cursor2.getString(0)
                val b = cursor2.getString(1)
                val c = cursor2.getString(2)
                listTD.add(ComicData(a,b,c))
            }
            while (cursor2.moveToNext()){
                val a = cursor2.getString(0)
                val b = cursor2.getString(1)
                val c = cursor2.getString(2)
                listTD.add(ComicData(a,b,c))
            }
        }

        followAdapter = FollowAdapter(requireActivity(),listTD)
        binding.listtheodoi.adapter = followAdapter

        binding.listtheodoi.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), InfoComicAcitivty::class.java)
            intent.putExtra("id_comic",listTD[position].id_comic)
            intent.putExtra("id",data)
            startActivity(intent)
        }

        binding.buttonDT.setOnClickListener {

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

    private fun level(a: Int): String{
        val x = when(a) {
            in 0..9 -> 1
            in 10..99 -> 2
            in 100..999 -> 3
            in 1000..9999 -> 4
            in 10000..99999 -> 5
            in 100000..999999 -> 6
            else -> 7
        }
        return Integer.toString(x)
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}