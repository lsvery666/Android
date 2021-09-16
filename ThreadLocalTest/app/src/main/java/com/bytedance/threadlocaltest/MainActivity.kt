package com.bytedance.threadlocaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val threadLocal = ThreadLocal<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        threadLocal.set(true)

        thread {
            threadLocal.set(false)
            Log.d(TAG, "[Thread1] threadLocal.get() = ${threadLocal.get()}")
         }

        thread {
            Log.d(TAG, "[Thread2] threadLocal.get() = ${threadLocal.get()}")
        }

        Log.d(TAG, "[MainThread] threadLocal.get() = ${threadLocal.get()}")

    }
}