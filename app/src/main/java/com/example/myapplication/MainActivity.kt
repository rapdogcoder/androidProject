package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private val viewModel : ViewModel by viewModels()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewmodel= viewModel
        binding.lifecycleOwner = this
    val RecordList = findViewById<RecyclerView>(R.id.recordlist)
    val adapter = Adapter(getRecordList())

        RecordList.layoutManager = LinearLayoutManager(this)
        RecordList.adapter = adapter


    }
    fun getRecordList() : List<Record> {
        val recordList = listOf(
            Record("00", ":00", ".00"),
            Record("00", ":00", ".00"),
            Record("00", ":00", ".00"),
            Record("00", ":00", ".00"),
            Record("00", ":00", ".00"),
        )
        return recordList
    }
}