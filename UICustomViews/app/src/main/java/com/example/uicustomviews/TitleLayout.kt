package com.example.uicustomviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.title.view.*

class TitleLayout(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        titleBack.setOnClickListener{
             val activity = context as Activity
            activity.finish()
        }
        titleEdit.setOnClickListener{
            Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show()
        }
    }
}