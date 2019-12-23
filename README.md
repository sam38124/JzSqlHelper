# JzSqlHelper
這是一套極致輕量化的sqlite管理器，使用最少的代碼進行和Database的交互！！
## 目录
* [如何導入到專案](#Import)
* [如何使用](#Use)
* [關於我](#About)

<a name="Import"></a>
### 如何導入到項目
> 支持jcenter。 <br/>

#### jcenter導入方式
在app專案包的build.gradle中添加
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

在需要用到這個庫的module中的build.gradle中的dependencies中加入
```
dependencies {
implementation 'com.github.sam38124:JzSqlHelper:v1.0'
}
```
<a name="Use"></a>
### 如何使用

第一步創建資料庫

```
val item= ItemDAO(this,"test.db")
```
第二步開始使用
```
# 使用ExSql執行資料庫語法


   item.ExSql("CREATE TABLE   IF NOT EXISTS logtable (\n" +
         "id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
         "    data VARCHAR NOT NULL,\n" +
         "    type VARCHAR NOT NULL\n" +
         ");\n")


# 使用Query進行資料庫查詢

   item.Query("select * from logtable" , Sql_Result {

            //返回colume1,2,3查詢到的資料(會反覆執行到資料行讀取完畢)
            val result1=it.getString(0)
            val result2=it.getString(1)
            val result3=it.getString(2)

        })

```
<a name="About"></a>
### 關於我
現任橙的電子全端app開發工程師

*line:sam38124

*gmail:sam38124@gmail.com
