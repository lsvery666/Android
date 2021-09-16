package com.example.viewtest01.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView

class ListViewEx : ListView {

    private val TAG = "MyListView"

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val res = super.onInterceptTouchEvent(ev)
        Log.d(TAG, "${MotionEvent.actionToString(ev.action)} onInterceptTouchEvent=$res")
        return res
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val res = super.onTouchEvent(ev)
        Log.d(TAG, "${MotionEvent.actionToString(ev.action)} onTouchEvent=$res")
        return res
    }


}