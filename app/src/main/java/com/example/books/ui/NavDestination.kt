package com.example.books.ui

interface Destination{
    val route:String
}
sealed class NavDestination{
    object AddBook:Destination {
        override val route: String
            get() = "Add Book"
    }
    object BookList:Destination{
        override val route: String
            get() = "Book List"

    }
}