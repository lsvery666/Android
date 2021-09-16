package com.bytedance.bindertest

import android.util.Log

interface SubInterface : SuperInterface {

    fun test2()

    companion object{
        class Impl: SubInterface{
            override fun test1() {
                Log.d("Impl", "test1()")
            }

            override fun test2() {
                Log.d("Impl", "test2()")
            }
        }
    }

}