package com.example.viewtest01.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton


class TestButton: AppCompatButton {
    private val TAG = TestButton::class.java.simpleName
    var lastX = 0
    var lastY = 0

    init {
        Log.d(TAG, "sts: ${ViewConfiguration.get(context).scaledTouchSlop}")
        text = "我可以滑动"
        setOnClickListener {
            Toast.makeText(context, "I'm clicked.", Toast.LENGTH_SHORT).show()
        }
    }

    constructor(context: Context): super(context, null){
        Log.d(TAG, "constructor(context: Context)")
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        Log.d(TAG, "constructor(context: Context, attrs: AttributeSet)")
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle){
        Log.d(TAG, "constructor(context: Context, attrs: AttributeSet, defStyle: Int)")
    }


    // 每一次event都会调用这个方法，所以lastX,lastY要记录在全局
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        event.let{
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()

            val a = intArrayOf(0, 0)
            val b = intArrayOf(0, 0)

            Log.d(TAG, ""+this.x)
            Log.d(TAG, ""+this.y)
            Log.d(TAG, ""+this.layoutParams.height)
            Log.d(TAG, ""+this.layoutParams.width)
            Log.d(TAG, ""+this.translationX)
            Log.d(TAG, ""+this.translationY)
            this.getLocationInWindow(a)
            this.getLocationOnScreen(b)
            Log.d(TAG, "${a[0]}" + " ${a[1]}")
            Log.d(TAG, "${b[0]}" + " ${b[1]}")

            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "ACTION_DOWN")
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = x - lastX
                    val deltaY = y - lastY
                    Log.d(TAG, "move, deltaX: $deltaX, deltaY: $deltaY")

                    // 方式1：scrollBy()/scrollTo()
                    // 特点：只能移动view的内容，不能移动view本身
//                    scrollBy(-deltaX, -deltaY)

                    // 方式2：View动画
                    translationX += deltaX
                    translationY += deltaY

                }
                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "ACTION_UP")
                    // onTouchEvent和onClick会冲突，必须显式调用callOnClick()方法
                    callOnClick()
                }
                else -> {}
            }
            lastX = x
            lastY = y
        }
        return true
    }


}