package com.bytedance.viewanimationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        val animation = AnimationUtils.loadAnimation(this, R.anim.animation_test)
        button1.setOnClickListener {
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            it.startAnimation(animation)
            AnimationSet(false).apply {

            }
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            Toast.makeText(this, "btn2 clicked", Toast.LENGTH_SHORT).show()
            button2.animate()
                .translationXBy(500f)     // 向右平移，单位：像素
                .setDuration(2 * 1000)
                .start()
        }

    }
}