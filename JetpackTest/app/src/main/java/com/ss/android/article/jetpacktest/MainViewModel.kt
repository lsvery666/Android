package com.ss.android.article.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int): ViewModel() {

    // 防止在ViewModel外修改LiveData
    // val没有setter
    val counter: LiveData<Int>
        get() = _counter

    private val _counter = MutableLiveData<Int>()

    init {
        _counter.value = countReserved
    }

    fun plusOne(){
        val value = _counter.value?: 0
        // 在子线程中要用post方法更新LiveData
        _counter.postValue(value + 1)
    }

    fun clear(){
        _counter.value = 0
    }

    private val userIdLiveData = MutableLiveData<String>()

    // 一旦userIdLiveData中的数据发生变化，那么观察userIdLiveData的switchMap方法就会执行，并且调用我们编写转换函数，获取真正的用户数据
    val user : LiveData<User> = Transformations.switchMap((userIdLiveData)){userId ->
        Repository.getUser(userId)
    }

    fun getUser(userId:String){
        userIdLiveData.value = userId
    }
}