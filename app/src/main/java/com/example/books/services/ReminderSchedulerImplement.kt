package com.example.books.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.ZoneId
import javax.inject.Inject

class ReminderSchedulerImplement @Inject constructor(private val context: Context):ReminderScheduler{

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(reminderItem: ReminderItem) {
        val intent = Intent(context, ReminerReceiver::class.java).apply {
            putExtra("MESSAGE","Time for reading ${reminderItem.book.name}")
        }
      alarmManager.setExactAndAllowWhileIdle(
          AlarmManager.RTC_WAKEUP,
          reminderItem.time.atZone(ZoneId.systemDefault()).toEpochSecond()*1000,
          PendingIntent.getBroadcast(context,
              reminderItem.hashCode(),
              intent,
              PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

      )
    }

    override fun cancel(reminderItem: ReminderItem) {
        alarmManager.cancel(

            PendingIntent.getBroadcast(context,
                reminderItem.hashCode(),
                Intent(context,ReminerReceiver::class.java)
                ,

                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        )
    }
}