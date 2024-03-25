package com.example.books.services

import com.example.books.database.Book
import java.time.LocalDateTime

data class ReminderItem(
    val time : LocalDateTime,
    val book:Book
)
