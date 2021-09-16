package com.bytedance.processtest

import android.app.ActivityManager
import android.content.Context

object ProcessUtils {

    /**
     * 通过ActivityManager获取到进程名字
     */
    fun getCurrentProcessName(context: Context):String?{
        val myPid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val runningAppProcesses = activityManager.runningAppProcesses
        if (!runningAppProcesses.isNullOrEmpty()){
            runningAppProcesses.forEach {
                if (it.pid == myPid){
                    return it.processName
                }
            }
        }
        return null
    }
}