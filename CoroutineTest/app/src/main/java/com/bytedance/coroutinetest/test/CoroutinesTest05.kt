package com.bytedance.coroutinetest.test

import android.os.Looper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// suspend关键字，使用它可以将任意函数声明成挂起函数，而挂起函数之间是可以互相调用的
// coroutineScope用于创建一个协程作用域
// coroutineScope函数只会阻塞当前协程，不会影响任何线程
suspend fun printDot() = coroutineScope{
    launch {
        for(i in 1..10) {
            println(".")
            delay(1000)
        }
    }
}

fun main() {
    runBlocking {
        printDot()
        println("coroutineScope finished")
    }
    println("runBlocking finished")
}