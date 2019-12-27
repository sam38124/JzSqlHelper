# JzSqlHelper
這是一套極致輕量化的sqlite管理器，使用最少的代碼進行和Database的交互！！
## 目錄
* [如何導入到專案](#Import)
* [快速使用](#Use)
* [預載Assets資料庫](#Assetsdb)
* [預載網路資料庫](#Urldb)
* [關於我](#About)

<a name="Import"></a>
### 如何導入到項目
> 支持jcenter。 <br/>

#### jcenter導入方式
在app專案包的build.gradle中添加
```kotlin
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

在需要用到這個庫的module中的build.gradle中的dependencies中加入
```kotlin
dependencies {
implementation 'com.github.sam38124:JzSqlHelper:5.1'
}
```
<a name="Use"></a>
### 快速使用

第一步創建資料庫

```kotlin
 val item= ItemDAO(this,"test.db").create()
```
第二步開始使用
```kotlin
# ExSql執行資料庫語法，Query查詢資料

        //創建資料表
        item.ExSql(
            "CREATE TABLE   IF NOT EXISTS logtable (\n" +
                    "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    data VARCHAR NOT NULL,\n" +
                    "    type VARCHAR NOT NULL\n" +
                    ");\n"
        )

        //插入資料
        item.ExSql("insert into logtable(data,type) values ('hello sql','sql')")

        //資料查詢
        item.Query("select * from logtable", Sql_Result {
            //Callback回調，會迴圈跑到所有資料載入完
            val result1 = it.getString(0)
            val result2 = it.getString(1)
            val result3 = it.getString(2)
        });

       //刪除資料表
       item.DropTb("Table")

       //關閉資料庫
       item.close()
```
<a name="Assetsdb"></a>
### 預載Assets資料庫
```kotlin
 val item = ItemDAO(this, "test.db")

        item.init_ByAsset("test.db", InitCaller {
        if (it) {
        item.create().Query("select count(1) from `Summary table`", Sql_Result { it ->
                    val result1 = it.getString(0)
                })
            }
        })
```
<a name="Urldb"></a>
### 預載網路資料庫
```kotlin
 val item = ItemDAO(this, "gg.db")

  item.init_ByUrl( "https://sampleurl/sample.db",InitCaller {
               if (it) {
               Log.e("預載", "success")
  item.create().Query("select count(1) from `Summary table`", Sql_Result { it ->
               var result = it.getString(0)
               handler.post {
               findViewById<TextView>(R.id.text).text = result
                        }
                    })
                } else {
                    Log.e("預載", "false")
                }
            })
```

<a name="About"></a>
### 關於我
橙的電子android and ios developer

*line:sam38124

*gmail:sam38124@gmail.com
