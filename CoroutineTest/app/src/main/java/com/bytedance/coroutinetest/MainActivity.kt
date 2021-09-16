package com.bytedance.coroutinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val SOURCE = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var handler : Handler? = null

        thread {
            Looper.prepare()
            handler = object: Handler(Looper.myLooper()!!){
                override fun handleMessage(msg: Message) {
                    if(msg.what == SOURCE){
                        Log.d("TAG", "receive msg")
                    }
                    super.handleMessage(msg)
                }
            }
            Looper.loop()
            Log.d(TAG, "handler thread finished")
        }

        thread {
            Thread.sleep(1000)
            handler?.sendEmptyMessage(SOURCE)
        }
    }
}