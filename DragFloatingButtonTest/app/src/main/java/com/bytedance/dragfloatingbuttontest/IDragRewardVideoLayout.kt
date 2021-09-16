package com.bytedance.dragfloatingbuttontest
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * author: zhangjiantao
 * date: 2021/3/25
 * desc:
 **/
abstract class IDragRewardVideoLayout: RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    protected var locationChangedListener: OnLocationChangedListener? = null

    open fun getLastLocation(res: IntArray) {

    }

    open fun setHorizontalMargin(margin: Int) {

    }


    open fun setOnLocationChangedListener(listener: OnLocationChangedListener?) {
        this.locationChangedListener = listener
    }


    interface OnLocationChangedListener {
        fun onLocationChanged(rawX: Int, rawY: Int)
    }
}