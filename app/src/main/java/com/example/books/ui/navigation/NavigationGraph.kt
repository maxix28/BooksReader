package com.example.books.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.books.ui.screens.AddBook
import com.example.books.ui.screens.BookList

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier= Modifier) {
    NavHost(navController = navController, startDestination = NavDestination.BookList.route) {
        composable(route = NavDestination.BookList.route) {
            BookList()
        }
        composable(route = NavDestination.AddBook.route) {
            AddBook()

        }

    }

}