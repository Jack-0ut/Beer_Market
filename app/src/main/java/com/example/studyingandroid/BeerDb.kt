package com.example.studyingandroid

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "beer_market"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "beer"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_COLOR = "color"

        private var instance: DbHelper? = null

        fun getInstance(context: Context): DbHelper {
            if (instance == null) {
                instance = DbHelper(context)
            }
            return instance!!
        }
    }
    // function that create the db
    override fun onCreate(db: SQLiteDatabase?) {
        // Creating the table
        val createTableSql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_COLOR TEXT)"
        db?.execSQL(createTableSql)
    }
    // override function that will work when we upgrade the table schema
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // drop the table before upgrade it
        val dropTableSql = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableSql)
        onCreate(db)
    }

    // insert new row into the 'beer' table
    fun  insertData(name: String, color: String): Boolean {
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_COLOR, color)
        }
        val result = writableDatabase.insert(TABLE_NAME, null, contentValues)
        // check if the data has been added and return the result
        return result != -1L
    }

    // return the list of the beer by the color
    @SuppressLint("Range")
    fun selectBeerByColor(attributeValue: String): ArrayList<String> {
        val resultList = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_COLOR = ?", arrayOf(attributeValue), null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                resultList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return resultList
    }

    // Select all the beer from db
    @SuppressLint("Range")
    fun selectAll(): ArrayList<String> {
        val resultList = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                resultList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return resultList
    }
}
