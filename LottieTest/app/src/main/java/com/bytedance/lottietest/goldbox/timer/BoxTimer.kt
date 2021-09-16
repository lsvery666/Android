package com.bytedance.lottietest.goldbox.timer

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bytedance.lottietest.goldbox.model.TimerInfo
import com.bytedance.news.ug.luckycat.goldbox.utils.FormatUtils

object BoxTimer {

    const val TAG = "BoxTimer"
    const val ERROR_CODE_TREASURE_BOX_UNKNOWN: Long = -1

    private var timerInfo: TimerInfo? = null // 宝箱的状态
    private var mOpenTreasureTimer: CountDownTimer? = null
    var timeTextLd = MutableLiveData<String>()

    fun clear() {
        timerInfo = null
    }

    private fun getTreasureBoxAvailableCountDownTime(ignoreElapseTime: Boolean): Long {
        val state = timerInfo ?: return ERROR_CODE_TREASURE_BOX_UNKNOWN
        val nextTreasureAvailableTime = state.nextTreasureTime
        if (nextTreasureAvailableTime <= 0 || state.responseTime <= 0) {
            return ERROR_CODE_TREASURE_BOX_UNKNOWN
        }
        val lotteryTime = timerInfo?.nextTreasureTime ?: 0
        var time = lotteryTime - state.getServerTime(ignoreElapseTime)
        if (time < 0) {
            time = 0
        }
        return time
    }

    fun startTiming(timerInfo: TimerInfo?): Long {
        BoxTimer.timerInfo = timerInfo

        mOpenTreasureTimer?.cancel()
        mOpenTreasureTimer = null

        val time = getTreasureBoxAvailableCountDownTime(true)
        Log.d(TAG, time.toString())
        if (time > 0) {
            mOpenTreasureTimer = object : CountDownTimer(time + 500L, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    val mills2str = FormatUtils.mills2str(millisUntilFinished) ?: ""
                    timeTextLd.postValue(mills2str)
                }

                override fun onFinish() {
                    timeTextLd.postValue("")
                }
            }.start()
        } else{
            timeTextLd.postValue("")
        }
        return time
    }


}


