package com.bytedance.activitytest

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        findViewById<Button>(R.id.showDialog).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("test")
                .show()
        }

        findViewById<Button>(R.id.noTitleBar).setOnClickListener {
            Dialog(this, android.R.style.Theme_Translucent_NoTitleBar).apply {
                setContentView(R.layout.layout_dialog)
                show()
            }
        }

        findViewById<Button>(R.id.fullScreenDialog).setOnClickListener {
            Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen).apply {
                setContentView(R.layout.layout_dialog)
                show()
            }
        }

        findViewById<Button>(R.id.showActivity).setOnClickListener {
            startActivity(Intent(this, NoContentActivity::class.java))
        }

        findViewById<Button>(R.id.translucentActivity).setOnClickListener {
            startActivity(Intent(this, TransparentActivity::class.java))
        }

        findViewById<Button>(R.id.thisActivity).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}