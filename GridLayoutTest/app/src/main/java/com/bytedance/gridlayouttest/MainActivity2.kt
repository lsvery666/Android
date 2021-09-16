package com.bytedance.gridlayouttest

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val gridLayout = GridLayout(this)

        val mStrings = listOf<String>("1", "2", "3", "4")

        // 2行   2列
        gridLayout.columnCount = 2
//        gridLayout.rowCount = 2

        for (i in mStrings.indices) {
            val textView = TextView(this)
            val params = GridLayout.LayoutParams()
            // 设置行列下标，和比重
            params.rowSpec = GridLayout.spec(i / 2, 1f)
            params.columnSpec = GridLayout.spec(i % 2, 1f)

            // 背景
            textView.setBackgroundColor(Color.BLACK)

            // 居中显示
            textView.gravity = Gravity.CENTER

            // 设置边距
            params.setMargins(4, 4, 0, 0)

            // 设置文字
            textView.setText(mStrings.get(i))

            // 添加item
            gridLayout.addView(textView, params)
        }

        addContentView(
            gridLayout,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
}