package com.example.books.database

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class Converter {
    @TypeConverter
    fun fromString(value: String): List<Quotes> {
        val listType = object : TypeToken<List<Quotes>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Quotes>): String {
        return Gson().toJson(list)
    }
}