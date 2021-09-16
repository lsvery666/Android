package com.bytedance.coroutinetest.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val job = Job()
    val scope = CoroutineScope(job)
    scope.launch {
        // 处理具体的逻辑
    }
    // 只需要调用一次cancel()方法，就可以将统一作用域内的所有协程全部取消
    job.cancel()
}

