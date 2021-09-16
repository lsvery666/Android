package com.bytedance.remoteviewstest

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, Activity2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 创建通知渠道 id name importance
        val channelId = "test"
        val channelName = "测试渠道"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        findViewById<Button>(R.id.test).setOnClickListener {
            Log.d("MainActivity", "onClick")
            val notification = NotificationCompat.Builder(this, channelId)
//                .setAutoCancel(true)
                .setContentTitle("content title")
                .setContentText("content text")
                .setTicker("ticker")
                .setWhen(1000)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(0, notification)
        }

        findViewById<Button>(R.id.cancel).setOnClickListener {
            notificationManager.cancel(0)
        }
    }

}