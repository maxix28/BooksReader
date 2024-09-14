package com.example.books.ui.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _firstName = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lastName = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _booksRead = mutableStateOf(0)
    val booksRead: State<Int> = _booksRead

    private val _status = mutableStateOf("")
    val status: State<String> = _status

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        _firstName.value = sharedPreferences.getString("first_name", "") ?: ""
        _lastName.value = sharedPreferences.getString("last_name", "") ?: ""
        println("Start!!!!!!!!!")
        _booksRead.value = bookRepository.getDoneBooks()
            .first() // Отримуємо перший результат з потоку
            .size
        println(_booksRead.value.toString())
        updateStatus()
    }

    fun saveUserData(firstName: String, lastName: String, booksRead: Int) {
        _firstName.value = firstName
        _lastName.value = lastName
        _booksRead.value = booksRead
        updateStatus()

        viewModelScope.launch {
            with(sharedPreferences.edit()) {
                putString("first_name", firstName)
                putString("last_name", lastName)
                putInt("books_read", booksRead)
                apply()
            }
        }
    }

    private fun updateStatus() {
        _status.value = when {
            _booksRead.value < 5 -> "Child"
            _booksRead.value < 20 -> "men"
            else -> "Guru"
        }
    }
}
