package com.bytedance.coroutinetest

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    // GlobalScope.launch是顶层协程，不会阻塞线程和协程
    GlobalScope.launch {
        println("codes run in coroutines scope")
        delay(1500)
        println("codes run in coroutines scope finished")
    }
    Thread.sleep(1000)
}