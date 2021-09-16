package com.bytedance.lottietest.goldbox.callback

interface BoxInfoCallback<T> {
    fun onSuccess(info: T)
    fun onFailed(error: String)
}