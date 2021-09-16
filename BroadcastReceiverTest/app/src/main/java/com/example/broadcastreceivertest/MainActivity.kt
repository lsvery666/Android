package com.example.broadcastreceivertest

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 动态注册
        receiver = MyReceiver()
        registerReceiver(receiver, IntentFilter("MyReceiver"))

        button.setOnClickListener {
            val intent = Intent("MyReceiver")
//            val intent = Intent(this, MyReceiver::class.java)     // 广播不能采用这种方法发送
            // 用户发出的自定义广播都是隐式广播，静态注册的BroadCastReceiver是无法接收隐式广播的
            // 因此，采用静态注册时，需要setPackage来指定这条广播时发给哪个应用程序的
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}