package com.example.viewtest01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val intent = Intent(this, TestActivity01::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this, TestActivity02::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(this, TestActivity03::class.java)
            startActivity(intent)
        }
    }



}