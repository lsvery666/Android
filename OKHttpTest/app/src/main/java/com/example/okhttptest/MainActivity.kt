package com.example.okhttptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var sendRequestBtn: Button? = null
    var responseText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendRequestBtn = findViewById<Button>(R.id.sendRequestBtn)
        responseText = findViewById<TextView>(R.id.responseText)
        sendRequestBtn!!.setOnClickListener {
//            sendHttpGetRequest("https://www.baidu.com", object : HttpCallBackListener{
//                // 接口中的方法还是在子线程中执行的，所以还是要借助runOnUiThread来更新UI
//                override fun onFinish(response: String) {
//                    showResponse(response)
//                }
//                override fun onError(e: Exception) {
//                    e.printStackTrace()
//                }
//            })

            sendOkHttpGetRequest("https://www.baidu.com", object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    // 这里的回调接口也是在子线程中
                    val responseBody = response.body?.string()
                    responseBody?.let {
                        showResponse(responseBody)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

            })
        }

    }

    // 方式一：使用HttpURLConnection
    private fun sendRequestByURLConnection() {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream

                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it + '\n')
                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }

        }
    }


    // 方式二：使用OkHttp
    private fun sendRequestByOkHttp() {
        // 耗时的操作要放在子线程去处理
        thread {
            try {
                val client = OkHttpClient()

                val requestBody = FormBody.Builder()
                    .add("username", "admin")
                    .add("password", "123456")
                    .build()

                val request = Request.Builder()
                    .url("https://www.baidu.com")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                val responseBody = response.body?.string()
                responseBody?.let {
                    showResponse(responseBody)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showResponse(str: String) {
        // 子线程不能直接更新UI
        runOnUiThread {
            responseText!!.text = str
        }
    }

    // 需求：写一个方法，发送HTTP请求，返回HTTP响应
    // 问题：子线程无法通过return返回数据
    // 解决：回调接口
    fun sendHttpGetRequest(url: String, listener: HttpCallBackListener) {
        thread {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()

                val responseBody = response.body?.string()!!
                listener.onFinish(responseBody)
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onError(e)
            }
        }
    }

    // OkHttp已经写好了回调接口
    fun sendOkHttpGetRequest(url: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        // OkHttp在enqueue方法的内部已经帮我们开好子线程了，然后会在子线程中执行HTTP请求
        client.newCall(request).enqueue(callback)
    }
}