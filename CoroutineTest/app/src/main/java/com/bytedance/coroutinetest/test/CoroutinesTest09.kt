package com.bytedance.coroutinetest.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// withContext 相当于 async{}.await()
// 区别在于withContext()必须指定一个线程参数
fun main() {
    runBlocking {
        val result = withContext(Dispatchers.Default){
            5 + 5
        }
        println(result)
    }
}