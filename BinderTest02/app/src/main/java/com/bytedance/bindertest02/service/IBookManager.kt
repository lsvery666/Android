package com.bytedance.bindertest02.service

import android.os.IBinder
import android.os.IInterface
import android.os.RemoteException
import com.bytedance.bindertest02.model.Book
import kotlin.jvm.Throws

/**
 * 服务端提供的接口
 */
interface IBookManager: IInterface {
    companion object{
        const val DESCRIPTOR = "com.bytedance.bindertest02.service.IBookManager"
        const val TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0
        const val TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1
    }

    @Throws(RemoteException::class)
    fun getBookList(): List<Book>?

    @Throws(RemoteException::class)
    fun addBook(book: Book?)
}