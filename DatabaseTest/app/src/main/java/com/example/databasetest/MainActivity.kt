package com.example.databasetest

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val myDatabaseHelper = MyDatabaseHelper(this, "BookStore.db", 2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BookStore.db是数据库名
        // Book是表名
        createDatabase.setOnClickListener {
            val db = myDatabaseHelper.writableDatabase  // 检查是否存在BookStore.db这个数据库，
                                                        // 如果不存在则执行onCreate()方法，于是就创建了Book表；
                                                        // 如果存在则不会执行onCreate()方法
        }

        addData.setOnClickListener {
            val db = myDatabaseHelper.writableDatabase
            val values1 = contentValuesOf("name" to "The Da Vinci Code", "author" to "Dan Brown", "pages" to 454, "price" to 16.96)
            db.insert("Book", null, values1)
            val values2 = contentValuesOf("name" to "The Lost Symbol", "author" to "Dan Brown", "pages" to 510, "price" to 19.95)
            db.insert("Book", null, values2)
        }

        updateData.setOnClickListener {
            val db = myDatabaseHelper.writableDatabase
            val values = contentValuesOf("price" to 10.99)
            db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }

        deleteData.setOnClickListener {
            val db = myDatabaseHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }

        queryData.setOnClickListener {
            val db = myDatabaseHelper.writableDatabase
            db.query("Book", null, null, null, null, null, null).apply{
                while(moveToNext()){
                    Log.d("MainActivity", "name: ${getString(getColumnIndex("name"))}")
                    Log.d("MainActivity", "author: ${getString(getColumnIndex("author"))}")
                    Log.d("MainActivity", "pages: ${getString(getColumnIndex("pages"))}")
                    Log.d("MainActivity", "name: ${getString(getColumnIndex("price"))}")
                    Toast.makeText(applicationContext, "name: ${getString(getColumnIndex("name"))}, " +
                            "author: ${getString(getColumnIndex("author"))}, " +
                            "pages: ${getString(getColumnIndex("pages"))}, " +
                            "name: ${getString(getColumnIndex("price"))}", Toast.LENGTH_SHORT).show()
                }
                close()
            }
        }
    }
}
