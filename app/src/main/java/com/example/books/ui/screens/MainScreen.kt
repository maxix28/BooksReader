package com.example.books.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.books.R
import com.example.books.ui.navigation.Destination
import com.example.books.ui.navigation.NavDestination
import com.example.books.ui.navigation.NavGraph
import com.example.compose.md_theme_light_primaryContainer
import okhttp3.Route

@Composable
fun mainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        bottomBar(navController = navController)
    }) { paddingValues ->
        NavGraph(
            modifier = modifier.padding(paddingValues = paddingValues),
            navController = navController
        )


    }

}

@Composable
fun bottomBar(modifier: Modifier = Modifier, navController: NavHostController) {

    val screens = listOf(
        NavDestination.BookList,
        NavDestination.AddBook,
        NavDestination.UserAccount,


        )

    val navBAckStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBAckStackEntry?.destination

    // BottomNavigation( modifier = modifier, contentColor = Color.Transparent, elevation = 10.dp) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navController = navController,
                currentDestination = currentDestination
            )
        }
    }
//    Column {
//        Divider(modifier = modifier.fillMaxWidth())
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 15.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Image(painter = painterResource(R.drawable.auto_stories_fill0_wght400_grad0_opsz24),
//                contentDescription = null,
//                modifier = modifier
//                    .size(25.dp)
//                    .clickable { }
//                    .padding(3.dp))
//            Image(painter = painterResource(R.drawable.add_box_fill0_wght400_grad0_opsz24),
//                contentDescription = null,
//                modifier = modifier
//                    .size(25.dp)
//                    .clickable { onAdd }
//                    .padding(3.dp))
//            Image(painter = painterResource(R.drawable.home_fill0_wght400_grad0_opsz24),
//                contentDescription = null,
//                modifier = modifier
//                    .size(25.dp)
//                    .clickable { }
//                    .padding(3.dp))
//
//        }
//    }


}

@Composable
fun RowScope.AddItem(
    screen: Destination,
    navController: NavHostController,
    currentDestination: androidx.navigation.NavDestination?, modifier: Modifier = Modifier
) {
//    BottomNavigationItem(selected = currentDestination?.hierarchy?.any {
//        it.route == screen.route
//    } == true, onClick = { navController.navigate(screen.route) }, icon = { painterResource(id = screen.icon) },
//        modifier = modifier.background(Color.Transparent),)

    IconButton(onClick = { navController.navigate(screen.route) }) {

        Icon(painter = painterResource(id = screen.icon),
            contentDescription = null,
            tint = colorScheme.primary,
            modifier = modifier
//                .background(if (currentDestination?.hierarchy?.any {
//                        it.route == screen.route
//                    } == true) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                .padding(5.dp)
//                .clip(
//                    androidx.compose.foundation.shape.CircleShape()
//                )
                .drawBehind {

                    drawCircle(
                        color = (if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                         md_theme_light_primaryContainer
                        } else Color.Transparent),
                        radius = (this.size.maxDimension/3)*2
                    )
                })
    }

}

//
@Preview(showBackground = true)
@Composable
fun botomprew() {
    bottomBar(navController = rememberNavController())
}