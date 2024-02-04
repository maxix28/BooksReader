package com.example.books.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddBook(
    modifier: Modifier = Modifier,
    addViewModel: AddBookViewModel = hiltViewModel<AddBookViewModel>()
) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


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
                onValueChange = {addViewModel.setExpectation(it)},
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
                          withContext(Dispatchers.IO){
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