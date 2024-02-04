package com.example.books.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BookRepository
import com.example.books.database.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class BooksListUI(
    val Books:List<Book> = emptyList()
)
@HiltViewModel
class BookListViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

    val AddUiState : StateFlow<BooksListUI> = bookRepository.getAllBooks().map { BooksListUI(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BooksListUI()
    )
}