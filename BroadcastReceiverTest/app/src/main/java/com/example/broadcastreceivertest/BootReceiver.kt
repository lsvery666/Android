package com.example.broadcastreceivertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("BOOT", "boot completed")
        Toast.makeText(context, "boot completed", Toast.LENGTH_LONG).show()
    }
}