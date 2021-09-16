package com.bytedance.lottietest.goldbox.model

import android.os.SystemClock

data class TimerInfo(
    val nextTreasureTime: Long,
    val responseTime: Long,
    val responseTimeStamp: Long
) {
    fun getServerTime(ignoreElapseTime: Boolean): Long {
        if (responseTime <= 0) {
            return 0
        }
        return if (ignoreElapseTime) { // 尽量保证和前端实现一样
            responseTime
        } else { // 冷启动接口返回和展示有时间差，需要减去接口返回时间
            responseTime + (SystemClock.elapsedRealtime() - responseTimeStamp)
        }
    }
}