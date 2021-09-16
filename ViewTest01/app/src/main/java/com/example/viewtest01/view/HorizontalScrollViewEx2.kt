package com.example.viewtest01.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewGroup
import android.widget.Scroller

class HorizontalScrollViewEx2 : ViewGroup {
    private var mChildrenSize = 0
    private var mChildWidth = 0
    private var mChildIndex = 0

    // 分别记录上次滑动的坐标
    private var mLastX = 0
    private var mLastY = 0

    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private val mLastXIntercept = 0
    private val mLastYIntercept = 0
    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        mScroller = Scroller(context)
        mVelocityTracker = VelocityTracker.obtain()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val action = event.action
        val res = if (action == MotionEvent.ACTION_DOWN) {
            mLastX = x
            mLastY = y
            if (!mScroller!!.isFinished) {
                mScroller!!.abortAnimation()
                return true
            }
            false
        } else {
            true
        }
        Log.d(TAG, "${MotionEvent.actionToString(event.action)} onInterceptTouchEvent=${res}")
        return res
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "${MotionEvent.actionToString(event.action)} onTouchEvent=true")
        mVelocityTracker!!.addMovement(event)
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                val scrollX = scrollX
                val scrollToChildIndex = scrollX / mChildWidth
                Log.d(
                    TAG,
                    "current index:$scrollToChildIndex"
                )
                mVelocityTracker!!.computeCurrentVelocity(1000)
                val xVelocity = mVelocityTracker!!.xVelocity
                mChildIndex = if (Math.abs(xVelocity) >= 50) {
                    if (xVelocity > 0) mChildIndex - 1 else mChildIndex + 1
                } else {
                    (scrollX + mChildWidth / 2) / mChildWidth
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1))
                val dx = mChildIndex * mChildWidth - scrollX
                smoothScrollBy(dx, 0)
                mVelocityTracker!!.clear()
                Log.d(
                    TAG,
                    "index:$scrollToChildIndex dx:$dx"
                )
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = 0
        var measuredHeight = 0
        val childCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(widthSpaceSize, childView.measuredHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            setMeasuredDimension(measuredWidth, heightSpaceSize)
        } else {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "width:$width")
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
        mScroller!!.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(mScroller!!.currX, mScroller!!.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracker!!.recycle()
        super.onDetachedFromWindow()
    }

    companion object {
        private const val TAG = "HorizontalScrollViewEx2"
    }
}