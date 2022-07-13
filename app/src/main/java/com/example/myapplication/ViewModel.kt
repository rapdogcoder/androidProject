package com.example.myapplication
import android.app.PendingIntent.getService
import android.content.ClipData
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import java.util.Timer
import kotlin.concurrent.timer


class ViewModel : ViewModel() {

    private val list = mutableListOf<Record>()
    private val _recordList = MutableLiveData<List<Record>>(list)
    val recordList: LiveData<List<Record>>
        get() = _recordList

    val minuteCount = MutableLiveData("00")
    val secondCount = MutableLiveData(":00")
    val milliCount = MutableLiveData(".00")
    val timerString = MutableLiveData("시작")


    var isRunning = false
    private var time = 0
    var timer : Timer? = null



    fun start(){
        if(!isRunning)
        {
            timerString.value = "정지"

        isRunning = true

        timer = timer(period = 10) {
            time ++
            val milli = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            milliCount.postValue(if (milli < 10) ".0${milli}" else ".${milli}")
            secondCount.postValue(if (second < 10) ":0${second}" else ":${second}")
            minuteCount.postValue(if (minute < 10) "0${minute}" else "$minute")
        }
        }
        else{
            stop()
        }
    }

    fun stop(){
        timerString.value = "시작"

        isRunning = false
        timer?.cancel()
    }

    fun reset(){
        timer?.cancel()

        timerString.postValue("시작")
        isRunning = false

        time = 0
        milliCount.postValue(".00")
        secondCount.postValue(":00")
        minuteCount.postValue("00")
        clearRecord()
    }

    fun addRecord(){
        val record = Record(minuteCount.value!!, secondCount.value!!, milliCount.value!!)
        list.add(record)
        _recordList.value = list
    }

    private fun clearRecord(){
        list.clear()
        _recordList.value = list
    }

}