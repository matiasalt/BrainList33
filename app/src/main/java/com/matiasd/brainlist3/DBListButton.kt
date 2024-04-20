package com.matiasd.brainlist3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBListButton(context: Context) : SQLiteOpenHelper(context, "ButtonDatabase", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuerry = "CREATE TABLE btnList (_id INTEGER PRIMARY KEY AUTOINCREMENT, listName TEXT, imageId INT)"
        db?.execSQL(createTableQuerry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}