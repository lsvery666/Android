package com.bytedance.bindertest02

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.bytedance.bindertest02.model.Book
import com.bytedance.bindertest02.aidl.BookManagerStub
import com.bytedance.bindertest02.service.BookService
import com.bytedance.bindertest02.service.IBookManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var bookManager: IBookManager? = null
    private val serviceConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = BookManagerStub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 打印当前进程PID
        Log.d(TAG, "pid:" + android.os.Process.myPid())
        // 当前线程
        Log.d(TAG, "thread name:${Thread.currentThread().name}, id:${Thread.currentThread().id}");

        // 通过Intent传递数据
        val intent = Intent(this, BookService::class.java)
        intent.putExtra("book", Book(0, "0号书"))

        bindService(
            intent,
            serviceConnection,
            BIND_AUTO_CREATE
        )

        var initId = 0
        val initName = "Book"
        addBtn.setOnClickListener {
            bookManager?.addBook(Book(initId++, initName))
        }

        getBtn.setOnClickListener {
            val result = bookManager?.getBookList()

            if(result == null || result.size == 0){
                textView.text = "No Book"
            }else {
                val sb = StringBuilder()
                for (book in bookManager?.getBookList()!!) {
                    sb.append("bookId: ${book.bookId} bookName: ${book.bookName}\n")
                }
                textView.text = sb.toString()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

}