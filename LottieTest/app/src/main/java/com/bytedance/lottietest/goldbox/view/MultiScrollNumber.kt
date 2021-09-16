package com.bytedance.lottietest.goldbox.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bytedance.lottietest.R
import com.bytedance.news.ug.luckycat.goldbox.view.ScrollNumber
import com.bytedance.news.ug.luckycat.goldbox.view.ScrollNumber.Companion.DEFAULT_VELOCITY


/**
 * Created by wuhaojie on 2016/7/19 20:39.
 */
class MultiScrollNumber:
    LinearLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    @SuppressLint("ResourceAsColor")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        val typedArray: TypedArray =
            mContext.obtainStyledAttributes(attrs, R.styleable.MultiScrollNumber)
        val numberSize = typedArray.getInteger(R.styleable.MultiScrollNumber_number_size, 20)

        setTextSize(numberSize)

        typedArray.recycle()
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    private val mContext: Context = context
    private val mTargetNumbers: MutableList<Int> = ArrayList()
    private val mPrimaryNumbers: MutableList<Int> = ArrayList()
    private val mScrollNumbers: MutableList<View> = ArrayList()
    private var mTextSize = 20
    private var mTextColors = intArrayOf(R.color.F04142)
    // AccelerateDecelerateInterpolator()
    // DecelerateInterpolator(1.75f)
    // AccelerateDecelerateInterpolator()
    private var mInterpolator: Interpolator = LinearInterpolator()
    private var mFontFileName: String? = null
    private var mVelocity = DEFAULT_VELOCITY

    fun initNum(str:String, isShow:Boolean){
        resetView()
        val charArray = str.toCharArray()
        for (i in charArray.indices) {
            if (Character.isDigit(charArray[i])) {
                mPrimaryNumbers.add(charArray[i] - '0')
            } else {
                mPrimaryNumbers.add(-1)
            }
        }
        for (i in mPrimaryNumbers.indices) {
            if (mPrimaryNumbers[i] != -1) {
                val scrollNumber = ScrollNumber(mContext)
                scrollNumber.setTextColor(
                    ContextCompat
                        .getColor(mContext, mTextColors[i % mTextColors.size])
                )
                scrollNumber.setDuration(1000 + i * 200L)
                scrollNumber.setVelocity(mVelocity)
                scrollNumber.setTextSize(mTextSize)
                scrollNumber.setInterpolator(mInterpolator)
                if (!TextUtils.isEmpty(mFontFileName)) scrollNumber.setTextFont(mFontFileName)
                scrollNumber.setNumber(mPrimaryNumbers[i], mPrimaryNumbers[i], 0, (i * 0).toLong())
                if(!isShow) scrollNumber.visibility = View.INVISIBLE
                mScrollNumbers.add(scrollNumber)
                addView(scrollNumber)
            } else {
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                val point = TextView(mContext)
                point.gravity = Gravity.BOTTOM
                point.text = "."
                point.setTextColor(
                    ContextCompat
                        .getColor(mContext, mTextColors[i % mTextColors.size])
                )
                point.textSize = mTextSize.toFloat()
                if(!isShow) point.visibility = View.INVISIBLE
                mScrollNumbers.add(point)
                addView(point, params)
            }
        }
    }

    fun scrollTo(str: String){
        mTargetNumbers.clear()
        val charArray = str.toCharArray()
        for (i in charArray.indices) {
            if (Character.isDigit(charArray[i])) {
                mTargetNumbers.add(charArray[i] - '0')
            } else {
                mTargetNumbers.add(-1)
            }
        }
        for (i in mTargetNumbers.indices) {
            if (mTargetNumbers[i] != -1) {
                val scrollNumber = mScrollNumbers[i] as ScrollNumber
                scrollNumber.visibility = View.VISIBLE
                scrollNumber.setNumber(mPrimaryNumbers[i], mTargetNumbers[i], 1, (i * 0).toLong())
            }else{
                (mScrollNumbers[i] as TextView).visibility = View.VISIBLE
            }
        }
    }

    private fun resetView() {
        mTargetNumbers.clear()
        mScrollNumbers.clear()
        removeAllViews()
    }

    fun setTextColors(@ColorRes textColors: IntArray?) {
        require(!(textColors == null || textColors.isEmpty())) { "color array couldn't be empty!" }
        mTextColors = textColors
        for (i in mScrollNumbers.indices.reversed()) {
            if(mScrollNumbers[i] is ScrollNumber){
                (mScrollNumbers[i] as ScrollNumber).setTextColor(
                    ContextCompat
                        .getColor(mContext, mTextColors[i % mTextColors.size])
                )
            }else{
                (mScrollNumbers[i] as TextView).setTextColor(
                    ContextCompat
                        .getColor(mContext, mTextColors[i % mTextColors.size])
                )
            }

        }
    }

    fun setTextSize(textSize: Int) {
        require(textSize > 0) { "text size must > 0!" }
        mTextSize = textSize
        for (s in mScrollNumbers) {
            if(s is ScrollNumber){
                (s as ScrollNumber).setTextSize(textSize)
            }else{
                (s as TextView).setTextSize(textSize.toFloat())
            }
        }
    }

    fun setInterpolator(interpolator: Interpolator?) {
        requireNotNull(interpolator) { "interpolator couldn't be null" }
        mInterpolator = interpolator
        for (s in mScrollNumbers) {
            if(s is ScrollNumber){
                s.setInterpolator(interpolator)
            }
        }
    }

    fun setTextFont(fileName: String?) {
        require(!TextUtils.isEmpty(fileName)) { "file name is null" }
        mFontFileName = fileName
        for (s in mScrollNumbers) {
            if(s is ScrollNumber){
                (s as ScrollNumber).setTextFont(fileName)
            }else{
                (s as TextView).setTypeface(Typeface.createFromAsset(mContext.assets, fileName))
            }
        }
    }

    fun setScrollVelocity(velocity: Float) {
        mVelocity = velocity
        for (s in mScrollNumbers) {
            if(s is ScrollNumber){
                (s as ScrollNumber).setVelocity(velocity)
            }
        }
    }

}