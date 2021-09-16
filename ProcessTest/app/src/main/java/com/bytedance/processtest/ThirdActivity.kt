package com.bytedance.processtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ThirdActivity : AppCompatActivity() {

    private val TAG = "ThirdActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Log.d(TAG, "onCreate ${android.os.Process.myPid()} ${ProcessUtils.getCurrentProcessName(applicationContext)}")

    }
}