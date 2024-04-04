package com.dicoding.appgithub

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context?) : SQLiteOpenHelper(context, "appgithub.db", null, 1) {
    override fun onCreate(DB: SQLiteDatabase) {
        DB.execSQL("create Table Fav(IdFav INTEGER PRIMARY KEY AUTOINCREMENT, Nama TEXT UNIQUE, Foto TEXT)")
    }

    override fun onUpgrade(DB: SQLiteDatabase, i: Int, i1: Int) {
        DB.execSQL("drop Table if exists Fav")
    }

    fun insertFav(
        Nama: String?,
        Foto: String?
    ): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Nama", Nama)
        contentValues.put("Foto", Foto)
        val result = DB.insert("Fav", null, contentValues)
        return if (result == -1L) {
            false
        } else {
            true
        }
    }

    fun deleteFav(IdFav: String): Boolean {
        val DB = this.writableDatabase
        val cursor = DB.rawQuery("Select * from Fav where IdFav = ?", arrayOf(IdFav))
        return if (cursor.count > 0) {
            val result =
                DB.delete("Fav", "IdFav=?", arrayOf(IdFav)).toLong()
            if (result == -1L) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }

    val dataFav: Cursor
        get() {
            val DB = this.writableDatabase
            return DB.rawQuery("Select * from Fav", null)
        }

}