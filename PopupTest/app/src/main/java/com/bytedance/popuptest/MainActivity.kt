package com.bytedance.popuptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var button: Button
    private lateinit var popupWindow : PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        val popup = LayoutInflater.from(this).inflate(R.layout.gold_box_unclickable_tips, null)
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)

//        val lp = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
//            gravity = Gravity.BOTTOM or Gravity.RIGHT
//        }
//        (button.parent as ViewGroup).addView(popup, lp)
//        (this.window.decorView as FrameLayout).addView(popup, lp)
        popupWindow = PopupWindow(popup, WRAP_CONTENT, WRAP_CONTENT, false)

        popup.findViewById<TextView>(R.id.popup_text).text = "稍后领取元"

        button.setOnClickListener {
//            Log.d(TAG, popup.measuredHeight.toString())
//            Log.d(TAG, popup.measuredWidth.toString())
            popup.measure(widthMeasureSpec, heightMeasureSpec)
            popupWindow.showAsDropDown(button, -(popup.measuredWidth - 84 * 4) / 2, -(84+21) * 4, Gravity.LEFT or Gravity.BOTTOM)
        }
    }

}