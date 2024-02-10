package com.example.books.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.books.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SearchBook(
    modifier: Modifier = Modifier,
    viewModel: AddBookViewModel = hiltViewModel<AddBookViewModel>()
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        OutlinedTextField(
//            value = viewModel.book,
//            onValueChange = { viewModel.book = it },
//            label = { Text("Enter your book") },
//            modifier = modifier.padding(5.dp),
//            singleLine = true
//        )
//        Button(onClick = { showResults = !showResults }) {
//            Text("Find book")
//        }

        val bookValue by viewModel.book.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        OutlinedTextField(value = bookValue,
            onValueChange = {
                viewModel.setBook(it)
                coroutineScope.launch {
                    viewModel.getBookApi()
                }


            }
        )
        val bookList by viewModel.bookApi.collectAsState()

        LazyColumn {
            items(bookList) { volumeInfo ->
                Log.d("volumeInfo", volumeInfo.toString())

//                Card (modifier = modifier
//                    .fillMaxWidth()
//                    .padding(10.dp)){
//                    Text(text = "Title: ${volumeInfo.title}")
//                    Text(text = "Author: ${volumeInfo.author}")
//                    AsyncImage(model = volumeInfo.imageUrl, contentDescription =null,    modifier = Modifier.size(80.dp),
//                        contentScale = ContentScale.FillBounds,
//                        alignment = Alignment.Center )
//
//                }
                SearchResultItem(volumeInfo)

            }

        }

    }
}

@Composable
fun SearchResultItem(searchInfo: SearchInfo, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { println(searchInfo.imageUrl) }
    ) {
        val image = searchInfo.imageUrl
val image2 ="https://books.google.com/books/content?id=vlL3cQAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"

        AsyncImage(
            model  = "https://books.google.com/books/content?id=vlL3cQAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
            contentDescription = "book cover",
            modifier = modifier.size(50.dp)
        )


        // Title and Author
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