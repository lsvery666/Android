package com.bytedance.viewpagertest03

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

object Utils {
    fun makeTextView(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.edit_text, null)
    }
}