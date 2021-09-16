package com.example.viewtest01.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.viewtest01.R

class MyDialog(context: Context, val position: IntArray): Dialog(context, R.style.Theme_ViewTest01) {

    private val root: ViewGroup

    val Int.dp: Float
        get() = context.resources.displayMetrics.density * this + 0.5f

    init {
        setContentView(R.layout.layout_full_screen)
        root = findViewById(R.id.root)
        addTestButton()
    }

    fun addTestButton(){
        //获取状态栏高度的资源id
        var result: Int = 0
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
        }

        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            marginStart = position[0]
            topMargin = position[1] - result
        }
        root.addView(TestButton(context), lp)
    }

}