package com.bytedance.okhttptest2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var executeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(1000)

        executeBtn = findViewById(R.id.execute_get)
        executeBtn.setOnClickListener {
            executeGet("https://www.baidu.com")
        }

    }

    private fun executeGet(url: String){
        thread {
            // 创建OkHttpClient
            val client = OkHttpClient.Builder().build()

            // 创建请求
            val request = Request.Builder()
                .url(url)
                .build()

            // 创建Call
            val call = client.newCall(request)

            // 同步执行
            val response = call.execute()
            Log.d(TAG, response.body?.string() ?: "null")
        }
    }
}

