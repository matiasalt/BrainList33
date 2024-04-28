package com.matiasd.brainlist3

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBListButton(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ButtonDatabase"
        private const val TABLE_NAME = "btnList"
        private const val COLUMN_NAME = "list_name"
        private const val COLUMN_ID = "image_id"
        private const val COLUMN_COLOR = "list_color"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuerry = "CREATE TABLE $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_ID INT, " +
                "$COLUMN_COLOR TEXT)"
        db?.execSQL(createTableQuerry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    @SuppressLint("Range")
    fun getDataFromSQLite(): List<Triple<String, Int, String>> {
        val dataList = mutableListOf<Triple<String, Int, String>>()
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_NAME, $COLUMN_ID, $COLUMN_COLOR FROM $TABLE_NAME"
        val cursor= db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR))
                dataList.add(Triple(name, id, color))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return dataList
    }

    fun dropTable(){
        val db = writableDatabase
        val dropTableQuerry= "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuerry)
        onCreate(db)
    }
}