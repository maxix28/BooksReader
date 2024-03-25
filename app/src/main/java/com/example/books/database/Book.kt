package com.example.books.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.sql.Time
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "MyBooks")
@TypeConverters(Converter::class)
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int= 0,
    var name:String,
    var author:String,
    var expectation: String,
    var pages:Int,
    var currentPage:Int = 0,
    var done:Boolean= false,
    @ColumnInfo( typeAffinity = ColumnInfo.BLOB)
    var ImageStr: ByteArray? = null,
    val startDate: Date = Date(System.currentTimeMillis()),
    //var startDate: LocalDateTime,
    var FinishDate:Date? = null,
    var totalTime:Long?= null,
    var Quotes:List<Quotes> = emptyList()

    )
