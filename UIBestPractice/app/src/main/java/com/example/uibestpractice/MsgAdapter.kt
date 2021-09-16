package com.example.uibestpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MsgAdapter(val msgs: List<Msg>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class LeftMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftMsg = itemView.findViewById<TextView>(R.id.leftMsg)
    }

    class RightMsgViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rightMsg = itemView.findViewById<TextView>(R.id.rightMsg)
    }

    override fun getItemViewType(position: Int): Int = msgs[position].type


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if(viewType == Msg.TYPE_RECEIVED){
            LeftMsgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent, false))
        }else{
            RightMsgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LeftMsgViewHolder -> holder.leftMsg.text = msgs[position].content
            is RightMsgViewHolder -> holder.rightMsg.text = msgs[position].content
        }
    }

    override fun getItemCount(): Int = msgs.size
}