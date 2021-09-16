package com.example.uibestpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val msgs = mutableListOf<Msg>()

    fun initData() {
        msgs.add(Msg("Hello guy.", Msg.TYPE_RECEIVED))
        msgs.add(Msg("Hello, who is that?", Msg.TYPE_SENT))
        msgs.add(Msg("This is Tom. Nice to meet you.", Msg.TYPE_RECEIVED))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MsgAdapter(msgs)
        send.setOnClickListener{
            val content = inputText.text.toString()
            if(content.isNotEmpty()){
                msgs.add(Msg(content, Msg.TYPE_SENT))
                // 当有新消息时，刷新RecyclerView中的显示
                recyclerView.adapter?.notifyItemInserted(msgs.size-1)
                // 将RecyclerView定位到最后一行
                recyclerView.scrollToPosition(msgs.size - 1)
                inputText.setText("")
            }
        }
    }
}