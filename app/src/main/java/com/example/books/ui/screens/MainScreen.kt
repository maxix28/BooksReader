package com.example.books.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.books.R
import com.example.books.ui.navigation.NavDestination
import com.example.books.ui.navigation.NavGraph

@Composable
fun mainScreen(modifier: Modifier = Modifier) {
 val    navCont = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {bottomBar(onAdd ={ navCont.navigate(NavDestination.AddBook.route)})}
    ) { paddingValues ->
        NavGraph(modifier= modifier.padding(paddingValues = paddingValues), navController = navCont)

    }

}

@Composable
fun bottomBar(modifier: Modifier = Modifier, onAdd:()->Unit) {


    Column {
        Divider(modifier = modifier.fillMaxWidth())
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(R.drawable.auto_stories_fill0_wght400_grad0_opsz24),
                contentDescription = null, modifier = modifier
                    .size(25.dp)
                    .clickable { }
                    .padding(3.dp)
            )
            Image(
                painter = painterResource(R.drawable.add_box_fill0_wght400_grad0_opsz24),
                contentDescription = null, modifier = modifier
                    .size(25.dp)
                    .clickable {onAdd }
                    .padding(3.dp)
            )
            Image(
                painter = painterResource(R.drawable.home_fill0_wght400_grad0_opsz24),
                contentDescription = null, modifier = modifier
                    .size(25.dp)
                    .clickable { }
                    .padding(3.dp)
            )

        }
    }



}

@Preview(showBackground = true)
@Composable
fun botomprew() {
    bottomBar(onAdd ={})
}