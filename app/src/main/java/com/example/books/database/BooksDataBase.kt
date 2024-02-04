package com.example.books.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Book::class), version = 1)
abstract  class BooksDataBase :RoomDatabase() {

    abstract fun bookDao():BookDao

    companion object{
        @Volatile
        private var Instance : BooksDataBase? = null

        fun getDataBase( context: Context): BooksDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BooksDataBase::class.java, "Books_database")

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }

    }
}