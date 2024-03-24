package com.example.books.services

interface ReminderScheduler {
    fun schedule(reminderItem: ReminderItem)
    fun cancel(reminderItem: ReminderItem)
}