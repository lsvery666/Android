package com.bytedance.listviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val names = mutableListOf<String>()
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repeat(50) {
            names.add("hhh$it")
        }

        listView = findViewById(R.id.list_view)
        listView.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                Log.d(TAG, "getCount")
                return names.size
            }

            override fun getItem(position: Int): Any {
                Log.d(TAG, "getItem")
                return names[position]
            }

            override fun getItemId(position: Int): Long {
                Log.d(TAG, "getItemId")
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//               if(convertView!=null) {
//                   Log.d(TAG, "$position: converterView!=null")
//                   return convertView
//               }
                Log.d(TAG, "$position: converterView==null")
                val itemView = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_name, null)
                itemView.findViewById<TextView>(R.id.item_text).text = names[position]
                return itemView
            }

        }

    }


}