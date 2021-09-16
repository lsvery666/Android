package com.bytedance.toasttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread{
            Looper.prepare()
            Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show()
        }
    }
}