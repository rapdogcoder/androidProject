package com.example.myapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.activity.viewModels
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    var mService: StopWatchService? = null
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = (p1 as StopWatchService.StopWatchBinder).
            getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
        }
    }
    private val viewModel : ViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewmodel= viewModel
        binding.lifecycleOwner = this

        adapter = Adapter(viewModel.recordList.value!!)

        binding.recordlist.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        viewModel.recordList.observe(this){
            adapter.updateList(it)
        }


    }
    override fun onResume(){
        super.onResume()

        if (mService==null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(
                    Intent(this,
                    StopWatchService::class.java)
                )
            } else {
                startService(
                    Intent(applicationContext,
                StopWatchService::class.java)
                )
            }

            val intent = Intent(this, StopWatchService::class.java)
            bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE)

        }
    }

    override fun onPause() {
        super.onPause()
        if(mService!=null){
            if(!mService!!.isActivate()){
                mService!!.stopSelf()
            }
            unbindService(mServiceConnection)
            mService = null
        }
    }

}