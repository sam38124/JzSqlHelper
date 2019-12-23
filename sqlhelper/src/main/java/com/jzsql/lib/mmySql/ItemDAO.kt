package com.jzsql.lib.mmySql;
import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ItemDAO(context: Context,DB_NAME:String) {
    companion object {
        val TAG = "ItemDAO"
    }

    //資料庫物件
    private var dbHelper = DatabaseHelper(context,DB_NAME)
     var db: SQLiteDatabase
    init {
        dbHelper.openDataBase()
        db = dbHelper.db
    }

    fun ExSql(SQL:String) {
        try{
            db.execSQL(SQL);
        }catch (E:Exception){
            Log.e("SQLERROR:",E.message)
        }
    }
fun DropTb(tb:String){
    try{
        db.execSQL("drop table  `logtable`")
    }catch (e:Exception){
        Log.e("SQLERROR:",e.message)
    }
}
   fun Query(sql:String,caller: Sql_Result){
       val result = db.rawQuery(
           sql, null
        )
       if (result.count > 0) {
           result.moveToFirst()
           do {
               caller.result(result)
           } while (result.moveToNext())
           // 關閉Cursor物件
           result.close()
           // 回傳結果
       } else {
           result.close()
       }
   }
    // 關閉資料庫，一般的應用都不需要修改
    fun close() {
        dbHelper.close()
    }
}