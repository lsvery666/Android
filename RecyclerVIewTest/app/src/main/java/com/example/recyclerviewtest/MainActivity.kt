package com.example.recyclerviewtest

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var rv: RecyclerView? = null
    var mDatas: ArrayList<String>? = null
    private val TAG = "MainActivity"

    fun initData() {
        mDatas = ArrayList()
        for (c in 0..100) {
            mDatas!!.add("" + c);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 第一步：准备数据
        initData()

        // 第二步：找到控件
        rv = findViewById<RecyclerView>(R.id.rv)

        // 第三步：设置布局管理者LayoutManager
        rv!!.layoutManager = LinearLayoutManager(this)

        // 第四步：设置适配器Adapter
        rv!!.adapter = MyAdapter(this)

        // 第五步（可选）：添加分割线
        rv!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))

    }

    // 自定义一个Adapter，实现RecyclerView.Adapter
    inner class MyAdapter(val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private var count = 0

        // 自定义一个ViewHolder，继承自RecyclerView.ViewHolder
        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv = itemView.findViewById<TextView>(R.id.tv_item)
        }

        // 重写方法onCreateViewHolder，返回MyViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder{
            Log.d(TAG, "onCreateViewHolder: ${count++}")
            return MyViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item, parent, false)
            );
        }


        // 重写方法onBindViewHolder
        override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: $position")
            holder.tv.text = mDatas!!.get(position)
        }

        // 重写方法getItemCount
        override fun getItemCount(): Int = mDatas!!.size

    }



}