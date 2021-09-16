package com.bytedance.executortest

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

object NetworkUtils {

    /**
     * 写一个方法，在主线程调用，返回网络请求结果
     */
    fun getBaidu(): String{

        val executorService = Executors.newSingleThreadExecutor();

        val baiduTask = object : Callable<String>{
            override fun call(): String {
                var connection: HttpURLConnection? = null
                try {
                    Thread.sleep(1000)
                    val url = URL("https://www.baidu.com")
                    connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 8000
                    connection.readTimeout = 8000

                    val response = StringBuilder()
                    val input = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(input))
                    reader.use {
                        reader.forEachLine {
                            response.append(it + '\n')
                        }
                    }
                    return response.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return e.toString()
                } finally {
                    connection?.disconnect()
                }
            }
        }

        val futureTask = executorService.submit(baiduTask)
        executorService.shutdown()
        return futureTask.get()
    }

}