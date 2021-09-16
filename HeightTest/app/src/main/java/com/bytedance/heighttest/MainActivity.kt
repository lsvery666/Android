package com.bytedance.heighttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getHeight.setOnClickListener {
            // 屏幕高度
            val screenHeight = Utils.getScreenHeight(this)
            val screenRealHeight = Utils.getScreenRealHeight(this)
            val navigationBarHeight = Utils.getNavigationBarHeight(this)
            val statusBarHeight = Utils.getStatusBarHeight(this)
            heightText.text = "screenHeight: $screenHeight \n" +
                    "screenRealHeight: $screenRealHeight \n" +
                    "navigationBarHeight: $navigationBarHeight \n" +
                    "statusBarHeight: $statusBarHeight"
        }

    }
}