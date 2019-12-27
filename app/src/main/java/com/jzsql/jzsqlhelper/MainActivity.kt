package com.jzsql.jzsqlhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //第一步創建資料庫
        val item= ItemDAO(this,"test.db")
        //第二步創建資料表
        item.ExSql("CREATE TABLE   IF NOT EXISTS logtable (\n" +
                "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data VARCHAR NOT NULL,\n" +
                "    type VARCHAR NOT NULL\n" +
                ");\n")
        //第三部插入資料
        item.ExSql("insert into logtable(data,type) values ('hello sql','sql')")
        //第四部資料查詢
        item.Query("select * from logtable", Sql_Result {
            val result1=it.getString(0)
            val result2=it.getString(1)
            val result3=it.getString(2)
            Log.e("sqlresult",it.getString(0))
        });
    }
}
