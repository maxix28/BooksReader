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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddBook(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Column {
            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text("Name")
            }, modifier = modifier.padding(10.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text("Author")
            }, modifier = modifier.padding(10.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text("Expectation")
            }, modifier = modifier.padding(10.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text("Pages")
            }, modifier = modifier.padding(10.dp))

        }

        Button(
            onClick = {},
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