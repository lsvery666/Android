package com.example.viewtest01.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView

class ListViewEx2 : ListView {
    private var mHorizontalScrollViewEx2: HorizontalScrollViewEx2? = null

    // 分别记录上次滑动的坐标
    private var mLastX = 0
    private var mLastY = 0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    fun setHorizontalScrollViewEx2(
        horizontalScrollViewEx2: HorizontalScrollViewEx2?
    ) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 在这里先设置了disallowIntercept为true，表示禁止拦截
                // 注意这个禁止拦截并不是只针对这一个动作的，而是针对这个父组件！！
                mHorizontalScrollViewEx2!!.requestDisallowInterceptTouchEvent(true)
                Log.d(TAG, "${MotionEvent.actionToString(event.action)} disallowIntercept=true")
            }
            MotionEvent.ACTION_MOVE -> {
                // 所以有了上面的设置，MOVE也不再会被拦截，父类的onInterceptTouchEvent不会执行
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                // 但是如果是横向滑动，该MOVE必须要被父类拦截，所以要设置disallowIntercept为false
                // 此时父类的onInterceptTouchEvent才会生效，然后去父类的onInterceptTouchEvent里拦截MOVE
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    Log.d(TAG, ""+Math.abs(deltaX)+ "   " + Math.abs(deltaY))
                    mHorizontalScrollViewEx2!!.requestDisallowInterceptTouchEvent(false)
                    Log.d(TAG, "${MotionEvent.actionToString(event.action)} disallowIntercept=false")
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, MotionEvent.actionToString(event.action))
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(event)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val res = super.onTouchEvent(ev)
        Log.d(TAG, "${MotionEvent.actionToString(ev.action)} onTouchEvent=$res")
        return res
    }

    companion object {
        private const val TAG = "ListViewEx2"
    }
}
