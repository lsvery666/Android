package com.bytedance.handlertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.sendMsg).setOnClickListener {
            Handler(Looper.getMainLooper()).sendEmptyMessage(FROM_SECOND_ACTIVITY)
        }
    }
}