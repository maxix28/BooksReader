package com.example.books.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream
import java.util.Date

@TypeConverters(Converters::class)
@Database(entities = arrayOf(Book::class), version = 7)
abstract  class BooksDataBase :RoomDatabase() {

    abstract fun bookDao():BookDao

    companion object{
        @Volatile
        private var Instance : BooksDataBase? = null

        fun getDataBase( context: Context): BooksDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BooksDataBase::class.java, "Books_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }

    }
}

class Converters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let { Date(it) }
    }
}