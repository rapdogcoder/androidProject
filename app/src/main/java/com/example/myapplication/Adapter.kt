package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRecordBinding

class Adapter(private var list: List<Record>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    init {
        list = list.reversed()
    }

    class ViewHolder(val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvMinute3.text = list[position].minute
        holder.binding.tvSecond3.text = list[position].second
        holder.binding.tvMillisecond3.text = list[position].milisec
    }

    override fun getItemCount() = list.size

    fun updateList(list: List<Record>){
        this.list = list.reversed()
        notifyDataSetChanged()
    }
}