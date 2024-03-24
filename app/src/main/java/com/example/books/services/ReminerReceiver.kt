package com.example.books.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminerReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("MESSAGE")?:return
        println(message)
    }
}