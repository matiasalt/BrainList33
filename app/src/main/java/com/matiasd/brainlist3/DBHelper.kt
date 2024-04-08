package com.matiasd.brainlist3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuerry= "CREATE TABLE userinfo (username TEXT, password TEXT, email TEXT)"
        db?.execSQL(createTableQuerry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuerry= "DROP TABLE IF EXISTS userinfo"
        db?.execSQL(dropTableQuerry)
        onCreate(db)
    }

    fun addNewUser(username: String, password: String, email: String) {
        val datos = ContentValues().apply {
            put("username", username)
            put("password", password)
            put("email", email)
        }

        val db = this.writableDatabase
        db.insert("userinfo", null, datos)
        db.close()
    }

    fun readUser(username: String, password: String): Boolean{
        val db = readableDatabase
        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query("userinfo", null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }
}

