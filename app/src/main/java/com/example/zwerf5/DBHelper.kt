package com.example.zwerf5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID

class DBHelper (context: Context): SQLiteOpenHelper(context, "Userdata",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Userdata (id TEXT primary key , ingredient TEXT, expiredate DATE)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Userdata")
    }

    fun saveuserdata(id: String, ingredient: String, expiredate: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        val id = UUID.randomUUID().toString()
        cv.put("id",id)
        cv.put("ingredient",ingredient)
        cv.put("expiredate",expiredate)
        val result = db.insert("Userdata",null,cv)
        if(result == -1.toLong()){
            return false
        }
        return true
    }

    fun gettext(): Cursor?{
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from Userdata order by expiredate",null)
        return cursor
    }

    fun updateuserdata(id: String, ingredient: String, expiredate: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("ingredient",ingredient)
        cv.put("expiredate",expiredate)
        val cursor: Cursor = db.rawQuery("select * from Userdata where id = ?", arrayOf(id))
        if (cursor.count>0) {
            val result = db.update("Userdata",cv,"id=?", arrayOf(id))
            return result != -1
        }
        else{
            return false
        }
    }

    fun deleteuserdata(id: String): Boolean {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("select * from Userdata where id = ?", arrayOf(id))
        if (cursor.count>0){
            val result = db.delete("Userdata","id=?", arrayOf(id))
            return result != -1
        }
        else{
            return false
        }
    }
}