package com.example.books.ui.screens

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BookRepository
import com.example.books.database.Book
import com.example.books.database.Quotes
import com.example.books.ui.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import java.util.Date
import javax.inject.Inject

sealed interface BookDetailUIState {
    object Loading : BookDetailUIState
    object Error : BookDetailUIState

    data class Success(var book: Book) : BookDetailUIState

}

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle,
    private val app: Application
) : ViewModel() {

    suspend fun deleteBook() {
        bookRepository.DeleteBook((UIState as BookDetailUIState.Success).book)

    }

    var UIState: BookDetailUIState by mutableStateOf(BookDetailUIState.Loading)
    private val bookId = checkNotNull(savedStateHandle[NavDestination.BookDetail.BookId]) ?: null

    private suspend fun getBookByID(id: Any) {
        try {
            val result = bookRepository.getBookByID((id as String).toInt())
            UIState = BookDetailUIState.Success(result)


            Log.d("Book", result.toString())
        } catch (e: Exception) {

            UIState = BookDetailUIState.Error
            Log.e("ERROR", e.message.toString())
        }

    }


    fun setPage(newPage: Int) {
        if (UIState is BookDetailUIState.Success) {
            val currentState = UIState as BookDetailUIState.Success

            val  updatedBook = if (newPage >= currentState.book.pages) {
                currentState.book.copy(
                    currentPage = newPage,
                    done = true,
                    FinishDate = Date(System.currentTimeMillis())
                )
            } else {
                currentState.book.copy(currentPage = newPage, done = false)
            }
            UIState = currentState.copy(book = updatedBook)
        }
    }

    fun setPhoto(it: Uri) {
        try {
            if (UIState is BookDetailUIState.Success) {
                val newImage = app.contentResolver.openInputStream(it)?.readBytes()
                newImage?.let {
                    val currentState = UIState as BookDetailUIState.Success
                    val updatedState =
                        currentState.copy(book = currentState.book.copy(ImageStr = it))
                    UIState = updatedState
                   // println(UIState)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun setTime(time:Long){
        if (UIState is BookDetailUIState.Success) {
            val currentState = UIState as BookDetailUIState.Success
            val updatedBook =  if(currentState.book.totalTime == null) {
               currentState.book.copy(totalTime = time)
            }else{
                currentState.book.totalTime?.let {

                     currentState.book.copy(totalTime =  it + time)
                }
            }



            UIState = currentState.copy(book = updatedBook!!)
        }


    }
    suspend fun addQuote(quote: Quotes) {
        if (UIState is BookDetailUIState.Success) {
            val currentState = UIState as BookDetailUIState.Success
            val updatedQuotes = currentState.book.Quotes.toMutableList()
            updatedQuotes.add(quote)
            val updatedBook = currentState.book.copy(Quotes = updatedQuotes)
            UIState = currentState.copy(book = updatedBook)
            // Print the updated book to verify if the quote is added
            Log.d("Book", updatedBook.toString())
        }
    }

    suspend fun UpdateBook() =
        bookRepository.UpdateBook((UIState as BookDetailUIState.Success).book)

    init {
        viewModelScope.launch {
            //withContext(Dispatchers.IO){
            getBookByID(bookId!!)
            // }
        }
    }

}