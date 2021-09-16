package com.bytedance.bindertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        SubInterface.Companion.Impl()
        Test.TAG
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            val screenPosition = intArrayOf(0, 0)
            textView.getLocationOnScreen(screenPosition)
            Log.d(TAG, "${screenPosition[0]}, ${screenPosition[1]}")

            val windowPosition = intArrayOf(0, 0)
            textView.getLocationOnScreen(windowPosition)
            Log.d(TAG, "${windowPosition[0]}, ${windowPosition[1]}")
        }
    }
}