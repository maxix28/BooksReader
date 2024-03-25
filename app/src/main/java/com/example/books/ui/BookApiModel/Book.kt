package com.example.books.ui.BookApiModel

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)