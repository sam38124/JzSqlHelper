package com.jzsql.lib.mmySql;

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

class ItemDAO(var context: Context, var DB_NAME: String) {
    companion object {
        val TAG = "ItemDAO"

    }

    var handle = Handler()
    //資料庫物件
    lateinit var dbHelper: DatabaseHelper
    lateinit var db: SQLiteDatabase
    fun create(): ItemDAO {
        dbHelper = DatabaseHelper(context, DB_NAME)
        dbHelper.openDataBase()
        db = dbHelper.db
        return this
    }

    fun DeleteDb(): Boolean {
        try {
            val DB_PATH = context.getDatabasePath(DB_NAME)
            return if (DB_PATH.exists()) {
                DB_PATH.delete()
            } else {
                true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun init_ByUrl(url: String, caller: InitCaller) {
        Thread {
            try {
                val input = URL(url).openStream()
                Dbinit(caller, input)
            } catch (e: Exception) {
                e.printStackTrace()
                handle.post { caller.Result(false) }
            }
        }.start()
    }

    fun init_ByAsset(filename: String, caller: InitCaller) {
        Thread {
            try {
                val input = context.assets.open(filename)
                Dbinit(caller, input)
            } catch (e: Exception) {
                e.printStackTrace()
                handle.post { caller.Result(false) }
            }
        }.start()
    }

    fun Dbinit(caller: InitCaller, stream: InputStream) {
        try {
            val DB_PATH = context.getDatabasePath(DB_NAME)
            val file = File(DB_PATH.path.replace(DB_NAME, ""))
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    caller.Result(false)
                }
            }
            val fos = FileOutputStream(DB_PATH.path)
            val bufferSize = 8192
            val buf = ByteArray(bufferSize)
            while (true) {
                val read = stream.read(buf)
                if (read == -1) {
                    break
                }
                fos.write(buf, 0, read)
            }
            stream.close()
            fos.close()
            val f = File(DB_PATH.path)
            if (f.exists() && f.isFile()) {
                Log.d("path", "" + f.length())
            } else {
                Log.d("path", "file doesn't exist or is not a file")
            }
            handle.post { caller.Result(f.length() != 0L) }
        } catch (e: Exception) {
            e.printStackTrace()
            handle.post { caller.Result(false) }
        }
    }

    fun ExSql(SQL: String) {
        try {
            db.execSQL(SQL);
        } catch (E: Exception) {
            Log.e("SQLERROR:", E.message)
        }
    }

    fun DropTb(tb: String) {
        try {
            db.execSQL("drop table  `$tb`")
        } catch (e: Exception) {
            Log.e("SQLERROR:", e.message)
        }
    }

    fun Query(sql: String, caller: Sql_Result) {
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