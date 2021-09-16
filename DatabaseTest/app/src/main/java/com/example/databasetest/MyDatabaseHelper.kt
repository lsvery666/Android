package com.example.databasetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(
    val context: Context?,
    name: String?,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {

    // v1.0
    private val createBook = "create table Book (" +
                            "id integer primary key autoincrement, " +
                            "author text, " +
                            "price real, " +
                            "pages integer, " +
                            "name text, " +
                            // v3.0新增需求
                            "category_id integer)"

    // v2.0
    private val createCategory = "create table Category (" +
                                "id integer primary key autoincrement, " +
                                "category_name text, " +
                                "category_code integer)"

    override fun onCreate(db: SQLiteDatabase) {
        // 创建表
        // v1.0 & v3.0
        db.execSQL(createBook)
        // v2.0
        db.execSQL(createCategory)
        Toast.makeText(context, "create new table: Book", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion <= 1){
            db.execSQL(createCategory)
        }
        if(oldVersion <= 2){
            db.execSQL("alter table Book add column category_id integer")
        }
    }

}