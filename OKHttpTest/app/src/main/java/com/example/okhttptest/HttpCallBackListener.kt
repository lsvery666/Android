package com.example.okhttptest

interface HttpCallBackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}