package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val list: List<Record>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    lateinit var recordList : List<Record>
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val minutetext: TextView
        val secondtext: TextView
        val millistext: TextView

        init{
            minutetext = itemView.findViewById(R.id.tv_minute3)
            secondtext = itemView.findViewById(R.id.tv_second3)
            millistext = itemView.findViewById(R.id.tv_millisecond3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.minutetext.text=list[position].minute
        holder.secondtext.text=list[position].second
        holder.millistext.text=list[position].milisec
    }

    override fun getItemCount() = list.size
}