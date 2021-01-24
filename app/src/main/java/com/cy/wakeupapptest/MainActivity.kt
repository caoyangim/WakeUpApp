package com.cy.wakeupapptest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cy.wakeupapptest.service.DetectService
import java.util.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button2)
        val timePicker = findViewById<TimePicker>(R.id.timepicker)
        btn.setOnClickListener {
//            startService(Intent(this,DetectService::class.java))
            val intent = Intent(this, ClockActivity::class.java)
            val pend = PendingIntent.getActivity(this@MainActivity, 0, intent, 0) //显示闹钟，alarmActivity
            val alarm =getSystemService(Context.ALARM_SERVICE) as AlarmManager // 通过Context.ALARM_SERVICE获取AlarmManager对象
            val calendar = Calendar.getInstance() //获取日历对象
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.currentHour) //利用时间拾取组件timePicker得到要设定的时间
            calendar.set(Calendar.MINUTE, timePicker.currentMinute)
            calendar.set(Calendar.SECOND, 0)
            alarm[AlarmManager.RTC, calendar.timeInMillis] = pend //设定闹钟

        }

        btn2.setOnClickListener {
            startService(Intent(this, DetectService::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //存值
        outState.putString("test", "test")
        Log.d("tag", "onSaveInstanceState:test ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //拿值，方式1
        val test = savedInstanceState.getString("test")
        Log.d("tag", "onRestoreInstanceState$test")
    }
}