package com.ss.android.article.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {

    fun getUser(userId: String): LiveData<User>{
        val liveData = MutableLiveData<User>()
        // 模拟从数据库中拿数据
        liveData.value = User("张", "三", 18, userId)
        return liveData
    }

}