package com.bytedance.scrollnumbertest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

/**
 * Created by wuhaojie on 2016/7/15 11:36.
 */
internal class ScrollNumber constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(mContext, attrs, defStyleAttr) {
    /**
     * number to - number from
     */
    private var mDeltaNum = 0f
    private var mCurNum = 0
    private var mInitNum = 0
    private var mNextNum = 0
    private var mTargetNum = 0
    private var mOffset = 0f
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var mTextCenterX = 0f
    private var mTextHeight = 0
    private val mTextBounds = Rect()
    private var mTextSize = sp2px(20f)
    private var mTextColor = -0x1000000
    private var mTypeface: Typeface? = null
    private var mVelocity = DEFAULT_VELOCITY

    private var mStart: Long = -1
    private var mDuration: Long = -1

    private val mScrollRunnable = Runnable {

        if (System.currentTimeMillis() - mStart < mDuration) {
            mOffset = -mDeltaNum * (System.currentTimeMillis() - mStart) / mDuration
        } else {
            mOffset = -mDeltaNum
        }
        invalidate()
        if (mOffset <= mInitNum - mCurNum - 1) {
            calNum(mCurNum + 1)
        }
    }

    companion object {
        const val TAG = "ScrollNumber"

        const val DEFAULT_VELOCITY = 0.1f
        const val DEFAULT_INIT_VELOCITY = 0.1f
    }

    init {
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = mTextSize.toFloat()
        mPaint.color = mTextColor
        if (mTypeface != null) mPaint.typeface = mTypeface
        measureTextHeight()
    }

    fun setDuration(duration: Long) {
        mDuration = duration
    }

    fun setVelocity(velocity: Float) {
        mVelocity = velocity
    }

    fun setNumber(from: Int, to: Int, mods: Int, delay: Long) {
        val target =
            if (to < from) {
                to + 10 * (mods + 1)
            } else {
                to + 10 * mods
            }
        postDelayed({
            setFromNumber(from)
            setTargetNumber(target)
            mDeltaNum = (target - from).toFloat()
        }, delay)
    }

    fun setTextSize(textSize: Int) {
        mTextSize = sp2px(textSize.toFloat())
        mPaint.textSize = mTextSize.toFloat()
        measureTextHeight()
        requestLayout()
        invalidate()
    }

    fun setTextFont(fileName: String?) {
        require(!TextUtils.isEmpty(fileName)) { "please check file name end with '.ttf' or '.otf'" }
        mTypeface = Typeface.createFromAsset(mContext.assets, fileName)
        if (mTypeface == null) throw RuntimeException("please check your font!")
        mPaint.typeface = mTypeface
        requestLayout()
        invalidate()
    }

    fun setTextColor(mTextColor: Int) {
        this.mTextColor = mTextColor
        mPaint.color = mTextColor
        invalidate()
    }

    fun setInterpolator(interpolator: Interpolator) {
        mInterpolator = interpolator
    }

    private fun measureTextHeight() {
        mPaint.getTextBounds(mCurNum.toString() + "", 0, 1, mTextBounds)
        mTextHeight = mTextBounds.height()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
        mTextCenterX = (measuredWidth - paddingLeft - paddingRight ushr 1).toFloat()
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                mPaint.getTextBounds("0", 0, 1, mTextBounds)
                result = mTextBounds.height()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, size) else result
        return result + paddingTop + paddingBottom + 10
    }

    private fun measureWidth(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                mPaint.getTextBounds("0", 0, 1, mTextBounds)
                result = mTextBounds.width()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, size) else result
        return result + paddingLeft + paddingRight + 10
    }

    override fun onDraw(canvas: Canvas) {

        if (mCurNum != mTargetNum) {
            if (mStart == -1L) {
                mStart = System.currentTimeMillis()
            }
            postDelayed(mScrollRunnable, 0)
        }

        canvas.translate(0f, ((mOffset - mOffset.toLong())* measuredHeight).toFloat())
        drawSelf(canvas)
        drawNext(canvas)
    }

    private fun setFromNumber(number: Int) {
        mInitNum = number
        mCurNum = number
        mNextNum = number + 1
        mOffset = 0f
        invalidate()
    }

    fun setTargetNumber(number: Int) {
        mTargetNum = number
        invalidate()
    }

    private fun calNum(number: Int) {
        mCurNum = number
        mNextNum = number + 1
    }

    private fun drawNext(canvas: Canvas) {
        val y = measuredHeight * 1.5f
        canvas.drawText((mNextNum % 10).toString(), mTextCenterX, y + mTextHeight / 2, mPaint)
    }

    private fun drawSelf(canvas: Canvas) {
        val y = measuredHeight / 2f
        canvas.drawText((mCurNum % 10).toString(), mTextCenterX, y + mTextHeight / 2, mPaint)
    }

    private fun sp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            dpVal,
            resources.displayMetrics
        ).toInt()
    }

}