package com.example.viewtest01.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

class HorizontalScrollViewEx : ViewGroup {
    private var mChildrenSize = 0
    private var mChildWidth = 0
    private var mChildIndex = 0

    // 分别记录上次滑动的坐标
    private var mLastX = 0
    private var mLastY = 0

    private var mScroller: Scroller = Scroller(context)
    private var mVelocityTracker: VelocityTracker = VelocityTracker.obtain()

    companion object {
        private const val TAG = "HorizontalScrollViewEx"
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        var intercepted = false
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                    intercepted = true
                }
                Log.d(TAG, "ACTION_DOWN onInterceptTouchEvent=$intercepted")
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                intercepted = abs(deltaX) > abs(deltaY)
                Log.d(TAG, "ACTION_MOVE onInterceptTouchEvent=$intercepted")
            }
            MotionEvent.ACTION_UP -> {
                intercepted = false
                Log.d(TAG, "ACTION_UP onInterceptTouchEvent=$intercepted")
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return intercepted
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracker.addMovement(event)
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
                Log.d(TAG, "ACTION_DOWN onTouchEvent=true")
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                scrollBy(-deltaX, 0)
                Log.d(TAG, "ACTION_MOVE onTouchEvent=true")
            }
            MotionEvent.ACTION_UP -> {
                val scrollX = scrollX
                val scrollToChildIndex = scrollX / mChildWidth
                mVelocityTracker.computeCurrentVelocity(1000)
                val xVelocity = mVelocityTracker.xVelocity
                mChildIndex = if (Math.abs(xVelocity) >= 50) {
                    if (xVelocity > 0) mChildIndex - 1 else mChildIndex + 1
                } else {
                    (scrollX + mChildWidth / 2) / mChildWidth
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1))
                val dx = mChildIndex * mChildWidth - scrollX
                smoothScrollBy(dx, 0)
                mVelocityTracker.clear()
                Log.d(TAG, "ACTION_UP onTouchEvent=true")
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(TAG, "onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val childCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (heightSpecMode == MeasureSpec.AT_MOST && widthSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            measuredHeight = childView.measuredHeight
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
        } else if(heightSpecMode == MeasureSpec.AT_MOST){
            val childView = getChildAt(0)
            measuredHeight = childView.measuredHeight
        }
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "onLayout")
        var childLeft = 0
        val childCount = childCount
        mChildrenSize = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != GONE) {
                val childWidth = childView.measuredWidth
                mChildWidth = childWidth
                childView.layout(
                    childLeft, 0, childLeft + childWidth,
                    childView.measuredHeight
                )
                childLeft += childWidth
            }
        }
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        Log.d(TAG, "ACTION_MOVE onTouchEvent=true")
        mScroller.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        Log.d(TAG, "computeScroll")
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow")
        mVelocityTracker.recycle()
        super.onDetachedFromWindow()
    }


}
