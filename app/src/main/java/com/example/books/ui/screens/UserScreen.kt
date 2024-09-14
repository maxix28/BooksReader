package com.example.books.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.books.R

//@Composable
//fun UserScreen(modifier : Modifier =Modifier){
//    Column(modifier = modifier.fillMaxSize(), horizontalAlignment =Alignment.CenterHorizontally ) {
//        Icon(painter = painterResource(id = R.drawable.account_circle_fill0_wght400_grad0_opsz24), contentDescription = null)
//
//    }
//}

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel<UserViewModel>(), modifier: Modifier= Modifier) {
    val firstName by viewModel.firstName
    val lastName by viewModel.lastName
    val booksRead by viewModel.booksRead
    val status by viewModel.status

    var tempFirstName by remember { mutableStateOf(firstName) }
    var tempLastName by remember { mutableStateOf(lastName) }
    var tempBooksRead by remember { mutableStateOf(booksRead.toString()) }

    // Статус редагування
    var isEditing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            Button(onClick = {
                // Переходимо в режим редагування
                isEditing = true
                tempFirstName = firstName // Завантажуємо існуючі значення для редагування
                tempLastName = lastName
                tempBooksRead = booksRead.toString()
            }) {
                Text("Edit")
            }
        }
        if (isEditing) {
            // Якщо режим редагування, показуємо TextField для редагування
            TextField(
                value = tempFirstName,
                onValueChange = { tempFirstName = it },
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = tempLastName,
                onValueChange = { tempLastName = it },
                label = { Text("Surname") }
            )
            Spacer(modifier = Modifier.height(8.dp))



            Button(onClick = {
                viewModel.saveUserData(tempFirstName, tempLastName, tempBooksRead.toIntOrNull() ?: 0)
                isEditing = false // Вихід з режиму редагування після збереження
            }) {
                Text("Save")
            }
        } else {
            // Якщо режим перегляду, показуємо текст
            Row(){
                Text(text = "Name: ",)
                Text(text = firstName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(){ Text(text = "Surname: ",)
                Text(text = lastName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(){ Text(text = "Read books: ",)
                Text(text = booksRead.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Row(){ Text(text = "Status: ",)
                Text(text = status, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }



        }
    }
}