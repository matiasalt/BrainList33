package com.matiasd.brainlist3

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBLists(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ListsDB"
        private const val TABLE_NAME = "listsDB"
        private const val COLUMN_USERID = "user_id"
        private const val COLUMN_NAME = "name_list"
        private const val COLUMN_ITEM = "item_title"
        private const val COLUMN_DESCRIPTION = "description"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuerry = "CREATE TABLE $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERID TEXT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_ITEM TEXT, " +
                "$COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuerry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    @SuppressLint("Range")
    fun getDataFromSQLite(userId: String, nameList: String): List<Pair<String, String>> {
        val dataList = mutableListOf<Pair<String, String>>()
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ITEM, $COLUMN_DESCRIPTION FROM $TABLE_NAME WHERE $COLUMN_USERID = '$userId' AND $COLUMN_NAME = '$nameList'"
        val cursor= db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val itemtitle = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                dataList.add(Pair(itemtitle, description))
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
        //DELETE ITEMS
    fun deleteList(userId: String, nombreLista: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_USERID = ? AND $COLUMN_NAME = ?", arrayOf(userId, nombreLista))
        db.close()
    }

        //DELETE ITEMS


}

