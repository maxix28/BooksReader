package com.example.books.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun AddBook(book: Book)

    @Update
    suspend fun UpdateBook(book: Book)

    @Delete
    suspend fun DeleteBook(book:Book)



    @Query("Select * from MyBooks")
    fun getAllBooks(): Flow<List<Book>>

    @Query("Select * from MyBooks where done = true")
    fun getDoneBooks(): Flow<List<Book>>


    @Query("Select * from MyBooks where done = false")
    fun getCurrentBooks(): Flow<List<Book>>

    @Query("Select * from MyBooks where id =:id")
    fun getBookByID(id:Int): Book






}