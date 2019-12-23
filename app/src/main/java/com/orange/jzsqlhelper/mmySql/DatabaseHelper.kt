package com.orange.jzsqlhelper.mmySql

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHelper(private val mContext: Context,val DB_NAME:String) : SQLiteOpenHelper(mContext, DB_NAME, null, 1) {
    lateinit var db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase) {
        this.db=db
        Log.e("db","create")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.db=db
        Log.e("db","delete")
    }

    /** This method open database for operations  */
    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        db = writableDatabase
        return db.isOpen
    }

    /** This method close database connection and released occupied memory  */
    @Synchronized
    override fun close() {
        db.close()
        SQLiteDatabase.releaseMemory()
        super.close()
    }

}