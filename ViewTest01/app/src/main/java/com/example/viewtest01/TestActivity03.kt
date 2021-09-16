package com.example.viewtest01

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.viewtest01.utils.MyUtils
import com.example.viewtest01.view.ListViewEx2
import kotlinx.android.synthetic.main.activity_test03.*
import java.util.*

class TestActivity03 : Activity() {


    companion object {
        private const val TAG = "TestActivity03"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test03)
        Log.d(TAG, "onCreate")
        initView()
    }

    private fun initView() {
        val screenWidth: Int = MyUtils.getScreenMetrics(this).widthPixels
        val screenHeight: Int = MyUtils.getScreenMetrics(this).heightPixels
        for (i in 0..2) {
            val layout = layoutInflater.inflate(
                R.layout.content_test03, container, false
            ) as ViewGroup
            layout.layoutParams.width = screenWidth
            val textView = layout.findViewById<View>(R.id.title) as TextView
            textView.text = "page " + (i + 1)
            layout.setBackgroundColor(
                Color.rgb(255 / (i + 1), 255 / (i + 1), 0)
            )
            createList(layout)
            container.addView(layout)
        }
    }

    private fun createList(layout: ViewGroup) {
        val listView = layout.findViewById<View>(R.id.list) as ListViewEx2
        val datas = ArrayList<String>()
        for (i in 0..49) {
            datas.add("name $i")
        }

        listView.adapter = ArrayAdapter(
            this,
            R.layout.content_list_item, R.id.name, datas
        )
        listView.setHorizontalScrollViewEx2(container)
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(
                this@TestActivity03, "click item",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d(TAG, "dispatchTouchEvent action:" + ev.action)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "onTouchEvent action:" + event.action)
        return super.onTouchEvent(event)
    }

}
