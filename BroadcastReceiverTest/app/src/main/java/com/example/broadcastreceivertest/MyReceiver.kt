package com.example.broadcastreceivertest

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context, "MyReceiver", Toast.LENGTH_LONG).show()
        AlertDialog.Builder(context).apply {
            setTitle("test")
            setMessage("test")
            setCancelable(false)
            setPositiveButton("OK"){dialog, which ->}
            setNegativeButton("cancel"){dialog, which ->}
            show()
        }

        Thread{
            for(i in 1..100){
                Log.d("Thread", i.toString())
            }
        }.start()
    }
}