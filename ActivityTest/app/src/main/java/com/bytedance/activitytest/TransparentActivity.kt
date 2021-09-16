package com.bytedance.activitytest

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TransparentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_transparent)
    }
}