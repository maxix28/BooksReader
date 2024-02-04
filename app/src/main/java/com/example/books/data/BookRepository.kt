package com.example.books.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.books.database.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun AddBook(book: Book)
    suspend fun UpdateBook(book: Book)
    suspend fun DeleteBook(book: Book)
    fun getAllBooks(): Flow<List<Book>>
    fun getDoneBooks(): Flow<List<Book>>
    fun getCurrentBooks(): Flow<List<Book>>
    fun getBookByID(id:Int): Book

}