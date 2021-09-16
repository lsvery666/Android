package com.bytedance.bindertest02.aidl

import android.annotation.SuppressLint
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException
import com.bytedance.bindertest02.model.Book
import com.bytedance.bindertest02.service.IBookManager
import kotlin.jvm.Throws

/**
 * 服务端对接口进行实现 相当于Stub
 */
abstract class BookManagerStub: Binder(), IBookManager {

    init {
        // 把Binder和DESCRIPTOR绑定，后续客户端通过这个DESCRIPTOR拿到这个Binder
        attachInterface(this, IBookManager.DESCRIPTOR)
    }

    companion object{
        fun asInterface(obj: IBinder?): IBookManager?{
            if(obj == null){
                return null
            }
            // 客户端通过这个DESCRIPTOR拿到这个Binder，也就是上一步attachInterface添加的
            val iInterface = obj.queryLocalInterface(IBookManager.DESCRIPTOR)
            // 如果客户端和服务端在同一个进程，这里直接返回binder对象本身
            if(iInterface!=null && iInterface is IBookManager){
                return iInterface as IBookManager
            }
            // 否则返回的是代理对象
            return Proxy(obj)
        }

        class Proxy(val mRemote: IBinder): IBookManager {

            fun getInterfaceDescription() = IBookManager.DESCRIPTOR

            // 客户端通过代理对象进行RPC
            // 实际上是执行IBinder::transact(code, data, reply, flags)方法
            // 服务端在Service的binder线程池中根据传入的参数code确定执行哪个方法，data是方法形参，result是方法返回结果
            // 这三个参数都是通过进程间通信传递，也就是内存映射
            @SuppressLint("Recycle")
            @Throws(RemoteException::class)
            override fun getBookList(): List<Book>? {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                val result: List<Book>?
                try{
                    data.writeInterfaceToken(IBookManager.DESCRIPTOR)
                    mRemote.transact(IBookManager.TRANSACTION_getBookList, data, reply, 0)
                    reply.readException()
                    result = reply.createTypedArrayList(Book)
                }finally {
                    reply.recycle()
                    data.recycle()
                }
                return result
            }

            @Throws(RemoteException::class)
            override fun addBook(book: Book?) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                try{
                    data.writeInterfaceToken(IBookManager.DESCRIPTOR)
                    if(book != null){
                        data.writeInt(1)
                        book.writeToParcel(data, 0)
                    }else{
                        data.writeInt(0)
                    }
                    mRemote.transact(IBookManager.TRANSACTION_addBook, data, reply, 0)
                    reply.readException()
                }finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            override fun asBinder(): IBinder {
                return mRemote
            }

        }
    }

    /**
     * IInterface接口下需要重写的方法
     */
    override fun asBinder(): IBinder {
        return this
    }

    /**
     * 重写Binder下面的onTransact方法
     * 实际上是在Service的binder线程池中根据传入的参数code确定执行哪个方法，data是方法形参，result是方法返回结果
     */
    @Throws(RemoteException::class)
    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when(code){
            INTERFACE_TRANSACTION -> {
                reply?.writeString(IBookManager.DESCRIPTOR)
                return true
            }
            IBookManager.TRANSACTION_getBookList -> {
                data.enforceInterface(IBookManager.DESCRIPTOR)
                reply?.writeNoException()
                reply?.writeTypedList(getBookList())
                return true
            }
            IBookManager.TRANSACTION_addBook -> {
                data.enforceInterface(IBookManager.DESCRIPTOR)
                if(data.readInt() != 0){
                    addBook(Book(data))
                }
                reply?.writeNoException()
                return true
            }
        }
        return super.onTransact(code, data, reply, flags)
    }
}