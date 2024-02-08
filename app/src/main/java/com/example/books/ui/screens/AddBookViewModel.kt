package com.example.books.ui.screens

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.books.data.BookRepository
import com.example.books.database.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

data class AddUiState(
    var name: String = "",
    var author: String = "",
    var expectation: String = "",
    var pages: String = "",
    var ImageStr: ByteArray? = null
) {
    fun toBook(): Book = Book(name = name, author =  author, expectation =  expectation, pages = pages.toInt(), ImageStr = ImageStr)
}

@HiltViewModel
class AddBookViewModel @Inject
constructor(private val bookRepository: BookRepository, private val app:Application) : ViewModel() {
    var UIState by mutableStateOf(AddUiState())
fun resetUI(){
    UIState = AddUiState()
}
    fun setAuthor(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(author = it)
    }

    fun setName(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(name = it)
    }

    fun setPages(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(pages = it)
    }

    fun setExpectation(it: String) {
        var curentUI = UIState
        UIState = curentUI.copy(expectation = it)
    }
    fun setPhoto(it: Uri) {
        var curentUI = UIState
       // UIState = curentUI.copy(ImageStr = it)
        try {
            val newImage = app.contentResolver.openInputStream(it)?.readBytes()
            newImage?.let {
                UIState = curentUI.copy(ImageStr = it)
                println(UIState)

            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    suspend fun AddBook()= bookRepository.AddBook(UIState.toBook())

    private fun getImageBytesFromUri(uri: Uri): ByteArray {
        // Implement the logic to convert the image URI to a byte array
        // You can use ContentResolver to open an InputStream from the URI and read bytes
        // This logic depends on how you want to handle image data
        // For simplicity, I'll provide a placeholder function here
        // Make sure to implement a proper solution based on your requirements

        val contentResolver = app.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream?.readBytes() ?: ByteArray(0)
    }
}