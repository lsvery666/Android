package com.bytedance.processtest

import android.app.Application
import android.util.Log

class MyApplication: Application() {

    private val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate ${android.os.Process.myPid()} ${ProcessUtils.getCurrentProcessName(applicationContext)}")
    }

}