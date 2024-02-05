package com.example.books.ui.screens

import android.media.Image
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.books.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddBook(
    modifier: Modifier = Modifier,
    addViewModel: AddBookViewModel = hiltViewModel<AddBookViewModel>()
) {


    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult ={uri -> selectedImageUri= uri
        addViewModel.setPhoto(uri!!)}
    )
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        IconButton(onClick = { singlePhotoPicker.launch(
          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        ) }, modifier = modifier.size(150.dp)) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.image_fill0_wght400_grad0_opsz24),
                    contentDescription = null, modifier = modifier.size(90.dp)
                )
                Text(text = "Add book photo", fontSize = 10.sp)
            }

        }


        Column {
            OutlinedTextField(
                value = addViewModel.UIState.name,
                onValueChange = { addViewModel.setName(it) },
                label = {
                    Text("Name")
                },
                modifier = modifier.padding(10.dp)
            )
            OutlinedTextField(value = addViewModel.UIState.author,
                onValueChange = { addViewModel.setAuthor(it) }, label = {
                    Text("Author")
                }, modifier = modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = addViewModel.UIState.expectation,
                onValueChange = { addViewModel.setExpectation(it) },
                label = {
                    Text("Expectation")
                },
                modifier = modifier.padding(10.dp)
            )
            OutlinedTextField(value = addViewModel.UIState.pages,
                onValueChange = {
                    addViewModel.setPages(it)
                }, label = {
                    Text("Pages")
                }, modifier = modifier.padding(10.dp)
            )

        }

        Button(
            onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        addViewModel.AddBook()
                    }
                }


            },
            modifier = modifier
                //    .background(MaterialTheme.colorScheme.primary)
                .clip(RoundedCornerShape(15.dp))

        ) {
            Text("Start reading", modifier = modifier.padding(10.dp))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun prev() {
    AddBook()
}