package com.cy.wakeupapptest.service

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.cy.wakeupapptest.ClockActivity
import com.cy.wakeupapptest.MainActivity
import com.cy.wakeupapptest.R
import com.cy.wakeupapptest.receiver.BootBroadcastReceiver
import java.util.*


class DetectService : Service() {

    private val canRun = true
    private val firstRun = true

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "WTF-->服务开启~", Toast.LENGTH_SHORT).show();

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //这里开辟一条线程,用来执行具体的逻辑操作:
        Thread { Log.d("BackService", Date().toString()) }.start()
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        //这里是定时的,这里设置的是每隔两秒打印一次时间=-=,自己改
//        val anHour = 20 * 1000
//        val triggerAtTime = SystemClock.elapsedRealtime() + anHour
//        val i = Intent(this, BootBroadcastReceiver::class.java)
//        val pi = PendingIntent.getBroadcast(this, 0, i, 0)
//        manager[AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime] = pi

        val time = 10*1000
        val triggerAtTime = SystemClock.elapsedRealtime() + time
        val pi = PendingIntent.getActivity(this,0, Intent(this,ClockActivity::class.java),FLAG_CANCEL_CURRENT)
        manager[AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime] = pi
//        return super.onStartCommand(intent, flags, startId)


        val notification =  Notification(R.mipmap.ic_launcher, "服务正在运行",System.currentTimeMillis());
        val notificationIntent = Intent(this, MainActivity::class.java);
        val pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent,0);
        val remoteView = RemoteViews(this.packageName,R.layout.notification);
        remoteView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        remoteView.setTextViewText(R.id.text , "Hello,this message is in a custom expanded view");
        notification.contentView = remoteView;
        notification.contentIntent = pendingIntent;
//        startForeground(1, notification);
        return Service.START_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(">>>","服务被销毁!!!")
    }
}