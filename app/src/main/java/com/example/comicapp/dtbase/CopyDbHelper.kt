package com.example.comicapp.dtbase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class CopyDbHelper(private var context: Context) {
    companion object{
        private val DB = "comic_database.sqlite"
    }
    fun openDatabase(): SQLiteDatabase{
        val dbFile = context.getDatabasePath(DB)
        val file = File(dbFile.toString())
        if(file.exists()){
            Log.e("ok","co file roi")
        }
        else{
            copyDatabase(dbFile)
        }
        return SQLiteDatabase.openDatabase(dbFile.path,null,SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File?) {
        val openDB = context.assets.open(DB)
        val outputStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while (openDB.read(buffer)>0){
            outputStream.write(buffer)
            Log.wtf("DB","Dang chep")
        }
        outputStream.flush()
        outputStream.close()
        openDB.close()
        Log.wtf("db","xong roi")
    }
}