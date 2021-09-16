package com.bytedance.displaymetricstest

import android.app.Dialog
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowMetrics
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // windowManager.defaultDisplay.getMetrics(displayMetrics)
        // windowManager.defaultDisplay过时，改为display
        // display?.getMetrics(displayMetrics)过时，改为 windowMetrics.getBounds()

        val curBounds = windowManager.currentWindowMetrics.bounds
        val maxBounds = windowManager.maximumWindowMetrics.bounds
        val sb = StringBuilder()
        sb.append("winManager.curWindowMetrics.bounds: ${curBounds.height()}, ${curBounds.width()}\n")
        sb.append("winManager.maxWindowMetrics.bounds: ${maxBounds.height()}, ${maxBounds.width()}\n")

        // display的另外两个方法
        val displayMetrics = DisplayMetrics()
        display?.getRealMetrics(displayMetrics)
        sb.append("display.getRealMetrics: ${displayMetrics.heightPixels}, ${displayMetrics.widthPixels}\n")

        val point = Point()
        display?.getRealSize(point)
        sb.append("display.getRealSize: ${point.y}, ${point.x}\n")

        sb.append("resources.displayMetrics: ${resources.displayMetrics.heightPixels}, ${resources.displayMetrics.widthPixels}\n")

        findViewById<TextView>(R.id.textView).text = sb.toString()
    }
}