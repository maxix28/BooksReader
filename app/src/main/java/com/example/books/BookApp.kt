package com.example.books

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.books.services.AlarmNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class BookApp:Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                AlarmNotificationService.REMINDER_CHANEL,
                "Alarm",
                NotificationManager.IMPORTANCE_DEFAULT

            )
            channel.description="Used for reminding "
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}