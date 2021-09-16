package com.bytedance.componentsender

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent()
            val component = ComponentName("com.bytedance.componentreceiver", "com.bytedance.componentreceiver.MainActivity")
            intent.setComponent(component)
            startActivity(intent)
        }

    }
}