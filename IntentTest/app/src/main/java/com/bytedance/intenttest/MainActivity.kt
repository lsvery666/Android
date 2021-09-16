package com.bytedance.intenttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val ACTION1 = "action1"
    private val ACTION2 = "action2"
    private val ACTION3 = "action3"


    private val mBroadCastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent == null){
                return
            }

            if(intent.action == ACTION1){
                Toast.makeText(this@MainActivity, "action1", Toast.LENGTH_SHORT).show()
            }

            if(intent.action == ACTION2){
                Toast.makeText(this@MainActivity, "action2", Toast.LENGTH_SHORT).show()
            }

            if(intent.action == ACTION3){
                Toast.makeText(this@MainActivity, "action3", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(mBroadCastReceiver, IntentFilter(ACTION1).apply { addAction(ACTION2) })

        findViewById<Button>(R.id.btn1).setOnClickListener {
            sendBroadcast(Intent(ACTION1))
        }

        findViewById<Button>(R.id.btn2).setOnClickListener {
            sendBroadcast(Intent(ACTION2))
        }

        findViewById<Button>(R.id.btn3).setOnClickListener {
            sendBroadcast(Intent(ACTION3))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadCastReceiver)
    }
}
