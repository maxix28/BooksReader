package com.example.books.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.books.database.Book

@Composable
fun BookList(modifier :Modifier = Modifier, booksViewModel :BookListViewModel = hiltViewModel<BookListViewModel>()){

}
@Composable
fun oneBook(modifier :Modifier = Modifier,book: Book){
    Card(){
        Row {

        }
    }

}