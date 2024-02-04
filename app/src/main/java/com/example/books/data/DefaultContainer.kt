package com.example.books.data

import com.example.books.database.Book
import com.example.books.database.BookDao
import kotlinx.coroutines.flow.Flow

class DefaultContainer(private val bookDao: BookDao):BookRepository {
    override suspend fun AddBook(book: Book) = bookDao.AddBook(book)

    override suspend fun UpdateBook(book: Book) = bookDao.UpdateBook(book)

    override suspend fun DeleteBook(book: Book) = bookDao.DeleteBook(book)

    override fun getAllBooks(): Flow<List<Book>> =bookDao.getAllBooks()
    override fun getDoneBooks(): Flow<List<Book>> = bookDao.getDoneBooks()

    override fun getCurrentBooks(): Flow<List<Book>> = bookDao.getCurrentBooks()
    override fun getBookByID(id: Int): Book =bookDao.getBookByID(id)
}