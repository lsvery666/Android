package com.bytedance.bindertest02.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.bytedance.bindertest02.aidl.BookManagerStub
import com.bytedance.bindertest02.model.Book

class BookService : Service() {

    private val TAG = "BookService"

    private var bookList: ArrayList<Book>? = null

    private val myBinder = object : BookManagerStub() {
        override fun addBook(book: Book?) {
            // 打印当前进程PID
            Log.d(TAG, "pid:" + android.os.Process.myPid())
            // 当前线程
            Log.d(TAG, "thread name:${Thread.currentThread().name}, id:${Thread.currentThread().id}");
            book?.let{bookList?.add(it)}
        }

        override fun getBookList(): List<Book>? {
            return bookList
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "service onCreate")
        // 打印当前进程PID
        Log.d(TAG, "pid:" + android.os.Process.myPid())
        // 当前线程
        Log.d(TAG, "thread name:${Thread.currentThread().name}, id:${Thread.currentThread().id}");
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "service onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "service onBind")

        val book = intent.getParcelableExtra<Book>("book")
        Log.d(TAG, "receive book from Intent: $book")

        bookList = ArrayList<Book>()
        return myBinder
    }


}