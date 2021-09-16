package com.bytedance.dragfloatingbuttontest

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Settings.canDrawOverlays( this)) {
            val intent =  Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"));
            startActivityForResult(intent, 10);
        }

        val floatingButton = LayoutInflater.from(this).inflate(R.layout.floating_button, null) as DragRewardVideoLayout
        floatingButton.setHorizontalMargin(36)
        floatingButton.setOnClickListener {
            Toast.makeText(this, "按钮被点击了", Toast.LENGTH_SHORT).show()
        }

        val mLayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
            PixelFormat.TRANSPARENT
        ).apply {
            x = 100
            y = 300
            gravity = Gravity.LEFT or Gravity.TOP
        }

        windowManager.addView(floatingButton, mLayoutParams)

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}