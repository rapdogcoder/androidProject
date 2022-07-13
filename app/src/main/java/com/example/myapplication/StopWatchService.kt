package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.myapplication.databinding.ActivityMainBinding


class StopWatchService : Service() {

    var mStopWatch: ViewModel? = null
    var mBinder: StopWatchBinder = StopWatchBinder()

    inner class StopWatchBinder : Binder() {
        fun getService(): StopWatchService {
            return this@StopWatchService
        }
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE)
                    as NotificationManager
            val mChannel = NotificationChannel(
                "CHANNEL_ID", "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        val notification: Notification = Notification.Builder(this, "CHANNER_ID")
            .setChannelId("CHANNEL_ID")
            .setSmallIcon(R.drawable.timer)
            .setContentTitle("스탑와치 앱")
            .setContentText("앱이 실행 중입니다.")
            .build()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    fun isActivate(): Boolean {
        return (mStopWatch != null && mStopWatch?.isRunning ?: false)
    }
}
