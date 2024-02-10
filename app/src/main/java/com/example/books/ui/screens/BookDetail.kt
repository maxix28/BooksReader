package com.example.books.ui.screens

import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.books.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BookDetail(
    modifier: Modifier = Modifier,
    bookDetailViewModel: BookDetailViewModel = hiltViewModel<BookDetailViewModel>(),
    NavigateBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier.fillMaxSize()) {
        when (bookDetailViewModel.UIState) {
            BookDetailUIState.Error -> {
                Text(text = "Error")
            }

            BookDetailUIState.Loading -> {
                Text(text = "Loading")
            }

            is BookDetailUIState.Success -> {
                BookSuccess(
                    bookDetailViewModel.UIState as BookDetailUIState.Success,
                    onPageChange = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                bookDetailViewModel.setPage(it)
                                bookDetailViewModel.UpdateBook()
                            }
                        }
                    },
                    onPhotoChange = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                bookDetailViewModel.setPhoto(it)
                                bookDetailViewModel.UpdateBook()
                            }
                        }
                    },
                    onDelete = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                bookDetailViewModel.deleteBook()
                            }
                        }
                    },
                    NavigateBack = NavigateBack
                )
            }
        }
    }
}

@Composable
fun BookSuccess(
    state: BookDetailUIState.Success,
    modifier: Modifier = Modifier,
    onPageChange: (Int) -> Unit,
    onPhotoChange: (Uri) -> Unit,
    onDelete: () -> Unit,
    NavigateBack: () -> Unit
) {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                try {
                    selectedImageUri = uri
                    onPhotoChange(uri!!)
                } catch (e: Exception) {
                    println("Image error ")
                }
            })
    val Book = state.book
    var progress: Float by remember {
        mutableStateOf(0f)
    }
    if (Book.currentPage != 0) {
        progress = ((Book.currentPage.toFloat()) / Book.pages.toFloat())
    }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val startDateString = dateFormat.format(Book.startDate)
    val finishDateString = dateFormat.format(Book.FinishDate?:Book.startDate)
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {

                NavigateBack()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
            IconButton(onClick = {
                onDelete()
                NavigateBack()
            }) {
                //Icon(Icons.Default.ArrowBack)
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.delete_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                )
            }

        }
        var rotate by remember {
            mutableStateOf(0f)
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (Book.ImageStr != null) {
                AsyncImage(
                    model = Book.ImageStr,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(200.dp)
                        .clip(
                            RoundedCornerShape(10)
                        )
                        .rotate(rotate)
                        .clickable { rotate += 90f },
                    alignment = Alignment.Center,

                    )
            } else {
                Image(painter = painterResource(id = R.drawable.image_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    modifier = modifier
                        .size(150.dp)
                        .clickable {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        })
            }

        }


        // Start Text  name / author

        Text(
            text = Book.name,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = modifier.padding(horizontal = 30.dp)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Book.author,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                modifier = modifier.padding(horizontal = 25.dp, vertical = 5.dp)
            )

            Text(
                text = startDateString,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                modifier = modifier.padding(horizontal = 25.dp, vertical = 5.dp)
            )

        }
// Text progress info
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            if (Book.done == true) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = modifier.padding(vertical = 5.dp)) {

                        Text(
                            text = "${Book.pages} of ${Book.pages} pages done ",
                            fontSize = 18.sp,
                            modifier = modifier.padding(horizontal = 15.dp, )
                        )
                        Icon(
                            painter =
                            painterResource(id = R.drawable.task_alt_fill0_wght400_grad0_opsz24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = finishDateString,
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,
                        modifier = modifier.padding(horizontal = 25.dp, vertical = 5.dp)

                    )
                }
            } else {
                LaunchedEffect(key1 = state.book) {

                    if (Book.currentPage != 0) {
                        progress = ((Book.currentPage.toFloat()) / Book.pages.toFloat())
                    }
                }
                val progressAnimation by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
                    label = ""
                )
                LinearProgressIndicator(
                    progress = progressAnimation,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                        .animateContentSize(),
                )
                Text(text = "${state.book.currentPage} of ${Book.pages} pages", fontSize = 13.sp)


            }
        }

        //change current page
        var currentPageText by remember { mutableStateOf(state.book.currentPage.toString()) }

        OutlinedTextField(
            value = currentPageText,
            onValueChange = {
                try {
                    currentPageText = it
                    onPageChange(it.toInt())
                } catch (e: Exception) {
                    Log.e("ERROR", e.message.toString())
                }
            },
            modifier = modifier
                .padding(10.dp)
                .width(200.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "current page") },
            singleLine = true,
            trailingIcon = {
                if (currentPageText.isNotEmpty()) {
                    IconButton(
                        onClick = { currentPageText = "" }
                    ) {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "Clear",
                            modifier = modifier.size(10.dp)
                        )
                    }
                }
            }
        )

//            OutlinedTextField(
//                value = state.book.currentPage.toString(), onValueChange = {
//                    try {
//
//                        onPageChange(it.toInt())
//                    } catch (e: Exception) {
//                        Log.e("ERROR", e.message.toString())
//                    }
//                }, modifier = modifier
//                    .padding(10.dp)
//                    .width(90.dp),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                label = { Text(text = "current page") }
//
//            )

    }
}

