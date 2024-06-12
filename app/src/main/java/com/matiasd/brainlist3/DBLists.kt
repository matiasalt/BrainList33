package com.matiasd.brainlist3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBLists(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "lists.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_LISTS = "lists_table"
        private const val COLUMN_USER_ID = "userId"
        private const val COLUMN_LIST_NAME = "listName"
        // Otros nombres de columnas...
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_LISTS + "("
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_LIST_NAME + " TEXT,"
                // Otros tipos de columnas...
                + " PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_LIST_NAME + "))")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LISTS")
        onCreate(db)
    }

    fun getDataFromSQLite(userId: String, listName: String): List<Pair<String, Boolean>> {
        val dataList = mutableListOf<Pair<String, Boolean>>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_LISTS,
            null,
            "$COLUMN_USER_ID=? AND $COLUMN_LIST_NAME=?",
            arrayOf(userId, listName),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val item = cursor.getString(cursor.getColumnIndexOrThrow("item"))
                val isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("isChecked")) > 0
                dataList.add(Pair(item, isChecked))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dataList
    }
}