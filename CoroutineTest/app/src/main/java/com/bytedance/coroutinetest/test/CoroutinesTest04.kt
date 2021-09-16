package com.bytedance.coroutinetest.test

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // 协程的高效性
    val start = System.currentTimeMillis()
    runBlocking {
        repeat(100000){
            launch {
                println(".")
            }
        }
    }
    val end = System.currentTimeMillis()
    println(end - start)
}