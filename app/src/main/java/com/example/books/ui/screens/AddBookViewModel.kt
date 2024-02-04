package com.example.books.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.books.data.BookRepository
import com.example.books.database.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AddUiState(
    var name: String = "",
    var author: String = "",
    var expectation: String = "",
    var pages: String = "",
    var ImageStr: String? = null
) {
    fun toBook(): Book = Book(name = name, author =  author, expectation =  expectation, pages = pages.toInt(),)
}

@HiltViewModel
class AddBookViewModel @Inject
constructor(private val bookRepository: BookRepository) : ViewModel() {
    var UIState by mutableStateOf(AddUiState())

    fun setAuthor(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(author = it)
    }

    fun setName(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(name = it)
    }

    fun setPages(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(pages = it)
    }

    fun setExpectation(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(expectation = it)
    }

    suspend fun AddBook()= bookRepository.AddBook(UIState.toBook())


}