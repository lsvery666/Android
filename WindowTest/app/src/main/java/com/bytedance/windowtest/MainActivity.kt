package com.bytedance.windowtest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.view.WindowManager.LayoutParams
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Settings.canDrawOverlays( this)) {
            val intent =  Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"));
            startActivityForResult(intent, 10);
        }

        val button = Button(this)
        button.text = "button"
        val mLayoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            LayoutParams.TYPE_APPLICATION_OVERLAY,
            LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_FOCUSABLE or LayoutParams.FLAG_SHOW_WHEN_LOCKED or LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
            PixelFormat.TRANSPARENT
        ).apply {
            x = 100
            y = 300
            gravity = Gravity.LEFT or Gravity.TOP
        }
//        window.insetsController?.hide(WindowInsets.Type.statusBars())
        window.statusBarColor = resources.getColor(R.color.purple_200)
        windowManager.addView(button, mLayoutParams)
//        window.addContentView(button, mLayoutParams)
//        (window.decorView as ViewGroup).addView(button, mLayoutParams)

        var rawX = 0f
        var rawY = 0f
        button.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    rawX = event.rawX
                    rawY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val offsetX = event.rawX - rawX
                    val offsetY = event.rawY - rawY
                    mLayoutParams.x += offsetX.toInt()
                    mLayoutParams.y += offsetY.toInt()
                    windowManager.updateViewLayout(button, mLayoutParams)
                }
            }
            rawX = event.rawX
            rawY = event.rawY
            false
        }

    }
}