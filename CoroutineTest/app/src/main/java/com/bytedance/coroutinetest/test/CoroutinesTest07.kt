package com.bytedance.coroutinetest.test

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// async函数必须在协程作用域当中才能调用，它会创建一个新的子协程并返回一个Deferred对象，调用await()可以获得执行结果
fun main() {
    // 串行执行
    runBlocking {
        val start = System.currentTimeMillis()
        val result1 = async {
            delay(1000)
            5 + 5
        }.await()
        val result2 = async {
            delay(1000)
            4 + 6
        }.await()
        println("result is ${result1 + result2}")
        val end = System.currentTimeMillis()
        println("cost ${end - start} ms.")
    }
}