package com.example.menutest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class TestActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
        Log.d("Test", "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "onDestroy")
    }
}