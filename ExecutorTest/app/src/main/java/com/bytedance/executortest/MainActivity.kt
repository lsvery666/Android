package com.bytedance.executortest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).apply {
            setOnClickListener {
                val response = NetworkUtils.getBaidu()
                Log.d("MainActivity", response)
            }
        }
    }
}