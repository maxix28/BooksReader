package com.example.books.ui.screens

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.graphics.Color
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.books.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSuccess(
    state: BookDetailUIState.Success,
    modifier: Modifier = Modifier,
    onPageChange: (Int) -> Unit,
    onPhotoChange: (Uri) -> Unit,
    onDelete: () -> Unit,
    NavigateBack: () -> Unit
) {

    var datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    var timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
    val finishDateString = dateFormat.format(Book.FinishDate ?: Book.startDate)
    // date picker component
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {

                TextButton(
                    onClick = {
                        showTimePicker = true
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {

                NavigateBack()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
            Row() {
                IconButton(onClick = {
                    showDatePicker = true
                   // showTimePicker = true

                }) {
                    //Icon(Icons.Default.ArrowBack)
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.reminder_add),
                        contentDescription = null,
                        modifier = modifier.size(35.dp)
                    )

                }
                IconButton(onClick = {
                    val question = AlertDialog.Builder(context)
                        .setTitle("Delete ${Book.name}?")
                        .setPositiveButton("Yes") { d, w ->
                            onDelete()
                            NavigateBack()
                        }
                        .setNegativeButton("No") { d, w ->

                        }
                        .show()


                }) {
                    //Icon(Icons.Default.ArrowBack)
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.delete_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                    )
                }
            }


        }

        //Image
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
                            modifier = modifier.padding(horizontal = 15.dp)
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

    }
}