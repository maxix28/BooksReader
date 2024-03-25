package com.example.books.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.books.R
import com.example.compose.md_theme_light_primaryContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.books.ui.screens.*
@Composable
fun SearchBook(
    modifier: Modifier = Modifier,
    viewModel: AddBookViewModel = hiltViewModel<AddBookViewModel>(), onAddBook: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val bookValue by viewModel.book.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        Row(modifier = modifier.fillMaxWidth()){
            OutlinedTextField(value = bookValue,
                onValueChange = {
                    viewModel.setBook(it)
                    coroutineScope.launch {
                        viewModel.getBookApi()
                    }


                },
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.9f),
                placeholder = { Text("Enter Book or Author") })


            IconButton(onClick = onAddBook) {

                Icon(painter = painterResource(id = R.drawable.add_box_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier

                        .padding(5.dp).
                    size(40.dp)

                    )
            }

        }


        val bookList by viewModel.bookApi.collectAsState()

        LazyColumn {
            items(bookList) { volumeInfo ->
               // Log.d("volumeInfo", volumeInfo.toString())


                SearchResultItem(volumeInfo, onAddBook = onAddBook)

            }


        }

    }
}

@Composable
fun SearchResultItem(searchInfo: SearchInfo, modifier: Modifier = Modifier, onAddBook: () -> Unit) {


    Card(modifier= modifier.fillMaxWidth().height(70.dp).padding(5.dp),
        onClick = {
            bookFromApi.name = searchInfo.title
            bookFromApi.author = searchInfo.author
            onAddBook()
        })
    {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = searchInfo.title)
            Text(
                text = "Author: ${searchInfo.author}",
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}



