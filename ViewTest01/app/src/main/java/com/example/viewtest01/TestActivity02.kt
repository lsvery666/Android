package com.example.viewtest01

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.example.viewtest01.utils.MyUtils
import com.example.viewtest01.view.HorizontalScrollViewEx
import kotlinx.android.synthetic.main.activity_test02.*
import java.util.*

class TestActivity02 : Activity() {

    companion object {
        private const val TAG = "TestActivity02"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test02)
        Log.d(TAG, "onCreate")
        initView()
    }

    private fun initView() {

        val screenWidth: Int = MyUtils.getScreenMetrics(this).widthPixels
        val screenHeight: Int = MyUtils.getScreenMetrics(this).heightPixels
        Log.d(TAG, "screenWidth: $screenWidth , screenHeight: $screenHeight")

        for (i in 0..2) {
            val layout = layoutInflater.inflate(
                R.layout.content_test02, mListContainer, false
            ) as ViewGroup
            // 这个背景色不会改变ListView的背景色，因为ListView有background属性
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0))

            val textView = layout.findViewById<View>(R.id.title) as TextView
            textView.text = "page " + (i + 1)
            createList(layout)
            mListContainer.addView(layout)
        }
    }

    private fun createList(layout: ViewGroup) {
        val listView = layout.findViewById<View>(R.id.list) as ListView
        val datas = ArrayList<String>()
        for (i in 0..49) {
            datas.add("name $i")
        }
        val adapter = ArrayAdapter(
            this,
            R.layout.content_list_item, R.id.name, datas
        )
        listView.adapter = adapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    this@TestActivity02, "click item",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}