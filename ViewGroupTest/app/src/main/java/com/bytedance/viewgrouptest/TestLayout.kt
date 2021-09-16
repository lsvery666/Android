package com.bytedance.viewgrouptest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class TestLayout: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle:Int): super(context, attributeSet, defStyle)

    val linearLayout: LinearLayout

    init {
        // 如果root为null或者attachToRoot为false，返回的是inflate出来的view
        // 如果root不为null且attachToRoot为true，返回的是root
//        linearLayout = LayoutInflater.from(context).inflate(R.layout.test_layout, this, true) as LinearLayout

        linearLayout = LayoutInflater.from(context).inflate(R.layout.test_layout, this, false) as LinearLayout
        addView(linearLayout, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        linearLayout.orientation = VERTICAL
        val textView = TextView(context)
        textView.text = "hhhhhh"
        linearLayout.addView(textView)

    }

//    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
//        linearLayout.addView(child, index, params)
//    }
}