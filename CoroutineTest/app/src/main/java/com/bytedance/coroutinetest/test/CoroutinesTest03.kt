package com.bytedance.coroutinetest.test

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // launch创建子协程，只能在协程作用域中调用
    // 如果外层作用域的协程结束了，该作用域下的所有子协程也会一同结束
    runBlocking {
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
    }
}