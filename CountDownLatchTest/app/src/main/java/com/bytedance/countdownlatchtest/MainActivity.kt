package com.bytedance.countdownlatchtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countDownLatch = CountDownLatch(2)

        thread{
            try {
                Thread.sleep(3000)
                Log.d(TAG, Thread.currentThread().name + " end.")
                countDownLatch.countDown()
            }catch (e:InterruptedException){
                Log.e(TAG, e.message ?: "null")
            }
        }

        thread{
            try {
                Thread.sleep(5000)
                Log.d(TAG, Thread.currentThread().name + " end.")
                countDownLatch.countDown()
            }catch (e:InterruptedException){
                Log.e(TAG, e.message ?: "null")
            }
        }

        countDownLatch.await(5000, TimeUnit.MILLISECONDS)
        Log.d(TAG, countDownLatch.count.toString())
        Log.d(TAG, "UI Thread")


    }
}