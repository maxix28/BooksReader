package com.example.books.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.books.R
import com.example.books.database.Book
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    booksViewModel: BookListViewModel = hiltViewModel<BookListViewModel>(),
    onDetail: (String) -> Unit
) {
    val UIState = booksViewModel.AddUiState.collectAsState()
    if (UIState.value.Books.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Start your reading journey")
            Image(
                painter = painterResource(id = R.drawable.keyboard_double_arrow_down_fill0_wght400_grad0_opsz24),
                contentDescription = null,
                modifier = modifier.size(40.dp)
            )
        }

    }
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp)
    ) {
        items(UIState.value.Books) {
            oneBook(book = it, onDetail = onDetail)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun oneBook(modifier: Modifier = Modifier, book: Book, onDetail: (String) -> Unit) {
    var progress: Float = 0.toFloat()
    if (book.currentPage != 0) {
        progress = ((book.currentPage.toFloat()) / book.pages.toFloat())
    }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val FinishDateString = dateFormat.format(book.startDate)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .combinedClickable(
                onClick = { onDetail(book.id.toString()) },
                onLongClick = {}
            ),
        //.height(.dp)
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        // onClick = { onDetail(book.id.toString()) }
    ) {
        Row {
            if (book.ImageStr == null) {
                Icon(
                    painter = painterResource(id = R.drawable.image_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    modifier = modifier
                        .padding(5.dp)
                        .size(50.dp)
                        .padding(5.dp)
                )

            } else {
                AsyncImage(
                    model = book.ImageStr,
                    contentDescription = "Translated description of what the image contains",
                    modifier = modifier
                        .padding(5.dp)
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(10)),
                    contentScale = ContentScale.Crop
                )

            }
            Column() {
                Text(
                    book.name, modifier = modifier.padding(
                        horizontal = 5.dp,
                        vertical = 2.dp
                    ),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(book.author)
                    Text(
                        "${book.pages} pages",
                        fontSize = 13.sp,
                        modifier = modifier.padding(horizontal = 5.dp)
                    )

                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (progress >= 1f) {

                     //   Text(text = "Done", fontSize = 14.sp)
                        Icon(painter = painterResource(id = R.drawable.task_alt_fill0_wght400_grad0_opsz24),
                            contentDescription = null, tint = MaterialTheme.colorScheme.primary )


                        Text( text = FinishDateString,
                            modifier = modifier.padding(horizontal = 5.dp),
                            fontSize = 13.sp)
                    } else{
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth(0.45f)
                            ,
                        )
                    }



                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun bookPreview() {
    oneBook(
        book = Book(
            name = "Test",
            pages = 100,
            expectation = "Good",
            author = "Person Person",
            currentPage = 101
        ), onDetail = {}
    )
//    BookList()
}