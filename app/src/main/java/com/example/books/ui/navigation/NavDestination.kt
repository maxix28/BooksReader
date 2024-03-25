package com.example.books.ui.navigation

import com.example.books.R

interface Destination {
    val route: String
    val icon: Int
}

sealed class NavDestination {
    object AddBook : Destination {
        override val route: String
            get() = "Add Book"
        override val icon: Int
            get() = R.drawable.add_box_fill0_wght400_grad0_opsz24
    }

    object BookList : Destination {
        override val route: String
            get() = "Book List"
        override val icon: Int
            get() = R.drawable.auto_stories_fill0_wght400_grad0_opsz24

    }

    object BookDetail : Destination {
        override val route: String
            get() = "Book List"
        const val BookId = "id"
        val routeWithArgs = "$route/{$BookId}"
        override val icon: Int = 0

    }
    object UserAccount : Destination {
        override val route: String
            get() = "User Account"
        override val icon: Int = R.drawable.account_circle_fill0_wght400_grad0_opsz24

    }
    object SearchBook : Destination {
        override val route: String
            get() = "Search Book"
        override val icon: Int  = R.drawable.add_box_fill0_wght400_grad0_opsz24


    }


}