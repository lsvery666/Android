package com.bytedance.handlertest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button

val FROM_SECOND_ACTIVITY = 1

class MainActivity : AppCompatActivity() {

    private val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            if(msg.what == FROM_SECOND_ACTIVITY){
                Log.d("MainActivity", "receive msg")
            }
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.goToActivity2).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}