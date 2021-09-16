package com.bytedance.messagertest.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.bytedance.messagertest.model.Book
import com.bytedance.messagertest.model.MsgTypes

class BookService : Service() {

    private val TAG = "BookService"

    // 通过Messenger进行IPC
    private val handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if(msg.what == MsgTypes.MSG_FROM_CLIENT){
                // 在多进程下不能使用obk传递自定义的Parcelable对象
//                Log.d(TAG, "receive msg.obj from client: ${msg.obj}")

                val bundle = msg.data
                bundle.classLoader = Book.javaClass.classLoader
                Log.d(TAG, "receive msg.data from client: ${bundle.getParcelable<Book>("book")}")

                val clientMessenger = msg.replyTo
                val message = Message.obtain()
                message.what = MsgTypes.MSG_FROM_SERVICE
                message.data.putString("reply", "received")
                clientMessenger.send(message)

            }else{
                super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return Messenger(handler).binder
    }
}