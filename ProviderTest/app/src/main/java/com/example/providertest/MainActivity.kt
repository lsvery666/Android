package com.example.providertest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bookId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData.setOnClickListener {
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            val values = contentValuesOf("name" to "A Clash of Kings", "author" to "George Martin", "pages" to 1040, "price" to 22.85)
            val newUri = contentResolver.insert(uri, values)
            bookId = newUri?.pathSegments?.get(1)
            Toast.makeText(applicationContext, "add 1 rows", Toast.LENGTH_SHORT).show()
        }

        queryData.setOnClickListener {
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            contentResolver.query(uri, null, null, null, null)?.apply{
                while(moveToNext()){
                    Log.d("MainActivity", "name: ${getString(getColumnIndex("name"))}")
                    Log.d("MainActivity", "author: ${getString(getColumnIndex("author"))}")
                    Log.d("MainActivity", "pages: ${getString(getColumnIndex("pages"))}")
                    Log.d("MainActivity", "name: ${getString(getColumnIndex("price"))}")
                    Toast.makeText(applicationContext, "name: ${getString(getColumnIndex("name"))}, " +
                            "author: ${getString(getColumnIndex("author"))}, " +
                            "pages: ${getString(getColumnIndex("pages"))}, " +
                            "name: ${getString(getColumnIndex("price"))}", Toast.LENGTH_SHORT).show()}
                close()
            }
        }

        updateData.setOnClickListener {
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                val values = contentValuesOf("name" to "A Storm of Swords", "pages" to 1216, "price" to 24.05)
                val rows = contentResolver.update(uri, values, null, null)
                Toast.makeText(applicationContext, "update $rows rows", Toast.LENGTH_SHORT).show()
            }
        }

        deleteData.setOnClickListener {
            bookId?.let{
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                val rows = contentResolver.delete(uri, null, null)
                Toast.makeText(applicationContext, "update $rows rows", Toast.LENGTH_SHORT).show()
            }
        }


    }
}