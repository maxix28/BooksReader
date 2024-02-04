package com.example.books.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyBooks")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int= 0,
    var name:String,
    var author:String,
    var expectation: String,
    var pages:Int,
    var currentPage:Int = 0,
    var done:Boolean= false,
    var ImageStr:String? = null
)
