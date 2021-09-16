package com.bytedance.processtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate ${android.os.Process.myPid()} ${ProcessUtils.getCurrentProcessName(applicationContext)}")

        startActivity(Intent(this, SecondActivity::class.java))
        startActivity(Intent(this, ThirdActivity::class.java))
    }
}