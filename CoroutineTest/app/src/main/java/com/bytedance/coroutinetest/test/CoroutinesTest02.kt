package com.bytedance.coroutinetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    // runBlocking会阻塞当前线程
    runBlocking {
        println("codes run in coroutine scope")
        delay(1500)
        println("codes run in coroutine scope finished")
    }
}