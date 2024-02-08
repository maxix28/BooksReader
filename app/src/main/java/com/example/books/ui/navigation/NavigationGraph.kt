package com.example.books.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.books.ui.screens.AddBook
import com.example.books.ui.screens.BookDetail
import com.example.books.ui.screens.BookList

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavDestination.BookList.route) {
        composable(route = NavDestination.BookList.route) {
            BookList(
            onDetail = {
                navController.navigate("${NavDestination.BookDetail.route}/$it")
            })
        }
        composable(route = NavDestination.AddBook.route) {
            AddBook(afterdAdd = {navController.navigate(NavDestination.BookList.route)})

        }
        composable(
            route = NavDestination.BookDetail.routeWithArgs,
            arguments = listOf(navArgument(NavDestination.BookDetail.BookId) {
                type = NavType.StringType
            })
        ) {
            BookDetail()
        }

    }

}

@Composable
fun MyBottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
      //  backgroundColor = MaterialTheme.colors.primary
    ) {

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination


//                BottomNavigationItem(
////                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
////                    label = { Text(text = NavDestination.BookDetail.route) },
//                    selected = currentDestination?.hierarchy?.any { it.route == NavDestination.BookDetail.route } == true,
//                    onClick = {
//                        navController.navigate(NavDestination.BookDetail.route) {
//                            // Pop up to the start destination of the graph to
//                            // avoid building up a large stack of destinations
//                            // on the back stack as users select items
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            // Avoid multiple copies of the same destination when
//                            // re-selecting the same item
//                            launchSingleTop = true
//                            // Restore state when re-selecting a previously selected item
//                            restoreState = true
//                        }
//                    }
//                )

        }

}

@Preview
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()
    MyBottomBar(navController = navController)
}