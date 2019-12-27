package com.jzsql.jzsqlhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.jzsql.lib.mmySql.InitCaller
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*method1*/
        //第一步創建資料庫
        val item = ItemDAO(this, "test.db").create()
        //第二步創建資料表
        item.ExSql(
            "CREATE TABLE   IF NOT EXISTS logtable (\n" +
                    "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    data VARCHAR NOT NULL,\n" +
                    "    type VARCHAR NOT NULL\n" +
                    ");\n"
        )
        //第三部插入資料
        item.ExSql("insert into logtable(data,type) values ('hello sql','sql')")
        //第四部資料查詢
        item.Query("select * from logtable", Sql_Result {
            //Callback回調，會迴圈跑到所有資料載入完
            val result1 = it.getString(0)
            val result2 = it.getString(1)
            val result3 = it.getString(2)
            findViewById<TextView>(R.id.text).text = result2
            Log.e("sqlresult", result2)
        });
        item.close()


        /*method2 預載Asset Db*/
//        val item = ItemDAO(this, "test.db")
//        item.init_ByAsset("test.db", InitCaller {
//            if (it) {
//                item.create().Query("select count(1) from `Summary table`", Sql_Result { it ->
//                    var result = it.getString(0)
//                    handler.post {
//                        findViewById<TextView>(R.id.text).text = result
//                    }
//                })
//
//            }
//        })


//        /*method2 預載網路 Db*/
//        val item = ItemDAO(this, "gg.db")
//        item.init_ByUrl(
//            "https://sampleurl/sample.db",
//            InitCaller {
//                if (it) {
//                    Log.e("預載", "success")
//                    item.create().Query("select count(1) from `Summary table`", Sql_Result { it ->
//                        var result = it.getString(0)
//                        handler.post {
//                            findViewById<TextView>(R.id.text).text = result
//                        }
//
//                    })
//                } else {
//                    Log.e("預載", "false")
//                }
//            })
//    }

        var handler = Handler()
    }
}