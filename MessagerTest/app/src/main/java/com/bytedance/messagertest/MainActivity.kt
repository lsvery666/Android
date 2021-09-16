package com.bytedance.messagertest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.messagertest.model.Book
import com.bytedance.messagertest.model.MsgTypes
import com.bytedance.messagertest.service.BookService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var serviceMessenger: Messenger

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceMessenger = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private val handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            if(msg.what == MsgTypes.MSG_FROM_SERVICE){
                Log.d(TAG, "receive msg from service: ${msg.data.getString("reply")}")
            }
            super.handleMessage(msg)
        }
    }
    private val myMessenger = Messenger(handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, BookService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)

        sendMessage.setOnClickListener {
            val message = Message.obtain()
            message.what = MsgTypes.MSG_FROM_CLIENT
//            message.obj = Book(0, "0号书")
            message.data.putParcelable("book", Book(0, "0号书"))
            message.replyTo = myMessenger
            serviceMessenger.send(message)
        }
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }
}