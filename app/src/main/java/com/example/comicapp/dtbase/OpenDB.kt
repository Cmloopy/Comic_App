package com.example.comicapp.dtbase
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OpenDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "comic_database.sqlite"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Không cần thực hiện bất kỳ thao tác tạo bảng nào ở đây nếu bạn chỉ muốn mở cơ sở dữ liệu
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Thực hiện các cập nhật cơ sở dữ liệu khi phiên bản thay đổi
    }
}