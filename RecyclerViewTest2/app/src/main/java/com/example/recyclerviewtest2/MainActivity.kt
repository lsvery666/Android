package com.example.recyclerviewtest2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    var mDatas = ArrayList<String>()
    var rv: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 第一步准备数据
        initData()

        // 第二步找到控件
        rv = findViewById<RecyclerView>(R.id.rv)

        // 第三步设置布局管理器
        rv!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

        // 第四步设置适配器
        rv!!.adapter = MyAdapter(this, mDatas)

        // 第五步设置间距
        val spacesValues = HashMap<String, Int>()
        spacesValues.put(SpacesItemDecoration.TOP_SPACE, 100)
        spacesValues.put(SpacesItemDecoration.BOTTOM_SPACE, 20)
        spacesValues.put(SpacesItemDecoration.LEFT_SPACE, 50)
        spacesValues.put(SpacesItemDecoration.RIGHT_SPACE, 0)
        rv!!.addItemDecoration(SpacesItemDecoration(2, spacesValues, true))


    }

    fun initData(){
        for(i in 1..101){
            mDatas.add(""+i)
        }
    }

    class MyAdapter(val context: Context, val mDatas: ArrayList<String>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val btn = itemView.findViewById<Button>(R.id.btn_item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.btn.text = mDatas.get(position)
        }

        override fun getItemCount(): Int {
            return mDatas.size
        }



    }


}