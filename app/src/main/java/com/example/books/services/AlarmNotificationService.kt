package com.example.books.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.books.R

class AlarmNotificationService(private  val context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun showNotification(text:String){
val notification = NotificationCompat.Builder(context, REMINDER_CHANEL)
    .setSmallIcon(R.drawable.ic_launcher_foreground)
    .setContentText(text)
    .setAutoCancel(true)
   // .setContentTitle("Alarm")
    .build()
    notificationManager.notify(1,notification)
}
    companion object{
        const val  REMINDER_CHANEL ="reminder"
    }
}