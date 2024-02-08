package com.example.books.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.books.R

@Composable
fun UserScreen(modifier : Modifier =Modifier){
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(painter = painterResource(id = R.drawable.account_circle_fill0_wght400_grad0_opsz24), contentDescription = null)
    }
}