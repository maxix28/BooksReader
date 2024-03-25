//package com.example.books.ui.screens
//
//import android.app.Application
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Build
//import android.util.Log
//import androidx.annotation.RequiresApi
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.books.data.BookRepository
//import com.example.books.database.Book
//
//import com.loopj.android.http.AsyncHttpClient
//import com.loopj.android.http.AsyncHttpResponseHandler
//
//import cz.msebera.android.httpclient.Header
//
//import dagger.hilt.android.lifecycle.HiltViewModel
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.combine
//
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.flow.update
//import org.json.JSONObject
//import java.io.ByteArrayOutputStream
//import java.io.FileInputStream
//import java.io.IOException
//
//import javax.inject.Inject
//
//data class AddUiState(
//    var name: String = "",
//    var author: String = "",
//    var expectation: String = "",
//    var pages: String = "",
//    var ImageStr: ByteArray? = null
//) {
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun toBook(): Book = Book(name = name, author =  author, expectation =  expectation, pages = pages.toInt(), ImageStr = ImageStr, )
//}
//
//object ImageUtils {
//
//    // Method to compress the bitmap
//    fun compressBitmap(context: Context, uri: String): ByteArray? {
//        val bitmap = decodeUri(context, uri)
//        val compressedBitmap = compressImage(bitmap)
//        return bitmapToByteArray(compressedBitmap)
//    }
//
//    // Method to decode the image from URI into a Bitmap
//    private fun decodeUri(context: Context, uri: String): Bitmap? {
//        var bitmap: Bitmap? = null
//        try {
//            val inputStream = FileInputStream(uri)
//            bitmap = BitmapFactory.decodeStream(inputStream)
//            inputStream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return bitmap
//    }
//
//    // Method to compress the image
//    private fun compressImage(image: Bitmap?): Bitmap? {
//        val outputStream = ByteArrayOutputStream()
//        image?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust quality as needed
//        return BitmapFactory.decodeStream(outputStream.toByteArray().inputStream())
//    }
//
//    // Method to convert Bitmap to byte array
//    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
//        val outputStream = ByteArrayOutputStream()
//        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//        return outputStream.toByteArray()
//    }
//}
//data class SearchInfo(val title:String, val Author:String)
//@HiltViewModel
//class AddBookViewModel @Inject
//constructor(private val bookRepository: BookRepository, private val app:Application) : ViewModel() {
//    var UIState by mutableStateOf(AddUiState())
//
//
//  //  var book = MutableStateFlow("") // MutableStateFlow for holding book query
//    private val bookApi = MutableStateFlow<List<SearchInfo>>(emptyList()) // MutableStateFlow for holding book list
//
//    fun setBook(book: String) {
//        this.book.update { book }
//        Log.d("BOOK",this.book.value)
//    }
//
//
//
//    fun getBookApi2(): Flow<List<SearchInfo>> {
//        return bookApi
//    }
//   // var book= mutableStateOf("")
//
//
//    var bookList =mutableListOf<SearchInfo>()
//
//    val client = AsyncHttpClient()
//    var book = MutableStateFlow("") //
//    //val url = MutableStateFlow("https://www.googleapis.com/books/v1/volumes?q=${book.value}&langRestrict=en")
//    val url = combine(book) { bookValue ->
//        "https://www.googleapis.com/books/v1/volumes?q=$bookValue&langRestrict=en"
//    }.stateIn(viewModelScope, SharingStarted.Lazily, "https://www.googleapis.com/books/v1/volumes?q=&langRestrict=en")
//
//
//
//    suspend fun getBookApi () {
//        url.collect { urlString ->
//            client.get(urlString, object : AsyncHttpResponseHandler() {
//                override fun onSuccess(
//                    statusCode: Int,
//                    headers: Array<out Header>?,
//                    responseBody: ByteArray?
//                ) {
//                    val result = responseBody?.let { String(it) }
//                    Log.d("BOOK", result.toString())
//                    try {
//                        val jsonObject = JSONObject(result)
//                        val itemsArray = jsonObject.getJSONArray("items")
//                        // var tempBookList =  mutableStateOf<MutableList<SearchInfo>>(mutableListOf())
//
//                        val tempBookList = MutableStateFlow<List<SearchInfo>>(emptyList())
//                        val newList = mutableListOf<SearchInfo>()
//
//                        for (i in 0 until itemsArray.length()) {
//                            val book = itemsArray.getJSONObject(i)
//                            val volumeInfo = book.getJSONObject("volumeInfo")
//                            val title = volumeInfo.getString("title")
//                            val authorsArray = volumeInfo.optJSONArray("authors")
//                            val author = authorsArray?.getString(0) ?: "Unknown Author"
//
//                            // Create a new SearchInfo object and add it to the new list
//                            newList.add(SearchInfo(title, author))
//                        }
//
//// Update the value of tempBookList with the new list
//                        tempBookList.value = newList
//                        bookApi.value = tempBookList.value
//                    } catch (e: Exception) {
//                        Log.e("BOOK", e.message.toString())
//                        Log.e("BOOK", url.toString())
//                    }
//                }
//
//                override fun onFailure(
//                    statusCode: Int,
//                    headers: Array<out Header>?,
//                    responseBody: ByteArray?,
//                    error: Throwable?
//                ) {
//                    Log.e("BOOK", error.toString())
//                    Log.e("BOOK", url.value)
//
//                }
//            }
//
//            )
//        }
//    }
//
//
//fun resetUI(){
//    UIState = AddUiState()
//}
//    fun setAuthor(it: String) {
//        var curentUI = UIState
//        UIState = curentUI.copy(author = it)
//    }
//
//    fun setName(it: String) {
//        var curentUI = UIState
//        UIState = curentUI.copy(name = it)
//    }
//
//    fun setPages(it: String) {
//        var curentUI = UIState
//        UIState = curentUI.copy(pages = it)
//    }
//
//    fun setExpectation(it: String) {
//        var curentUI = UIState
//        UIState = curentUI.copy(expectation = it)
//    }
//
//
//    fun setPhoto(uri: Uri) {
//        try {
//            val compressedImage = compressImage(uri)
//            UIState = UIState.copy(ImageStr = compressedImage)
//            println(UIState)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//private fun compressImage(uri: Uri): ByteArray? {
//    return try {
//        val inputStream = app.contentResolver.openInputStream(uri)
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        inputStream?.close()
//        val outputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust quality as needed
//        outputStream.toByteArray()
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun AddBook()= bookRepository.AddBook(UIState.toBook())
//
//
//}


package com.example.books.ui.screens

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BookRepository
import com.example.books.database.Book

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import cz.msebera.android.httpclient.Header

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException

import javax.inject.Inject

data class AddUiState(
    var name: String = "",
    var author: String = "",
    var expectation: String = "",
    var pages: String = "",
    var ImageStr: ByteArray? = null
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toBook(): Book = Book(name = name, author =  author, expectation =  expectation, pages = pages.toInt(), ImageStr = ImageStr, )
}

@HiltViewModel
class AddBookViewModel @Inject
constructor(private val bookRepository: BookRepository, private val app:Application) : ViewModel() {
    var UIState by mutableStateOf(AddUiState())

    private val client = AsyncHttpClient()
    var book = MutableStateFlow("") //
   val bookApi = MutableStateFlow<List<SearchInfo>>(emptyList()) // MutableStateFlow for holding book list

    val url = book.map { bookValue ->
        "https://www.googleapis.com/books/v1/volumes?q=$bookValue&langRestrict=en+ua"
    }.stateIn(viewModelScope, SharingStarted.Lazily, "https://www.googleapis.com/books/v1/volumes?q=&langRestrict=en+ua")

    init {
        viewModelScope.launch {
            getBookApi()
        }
        if (bookFromApi.author != null) {
             setAuthor(bookFromApi.author.toString())
        }
        if (bookFromApi.name != null) {
         setName(bookFromApi.name.toString())
        }
        bookFromApi.name= null
        bookFromApi.author= null
    }

    suspend fun getBookApi() {
        url.collectLatest { urlString ->
            client.get(urlString, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    val result = responseBody?.let { String(it) }
                    //Log.d("BOOK", result.toString())
                    try {
                        val jsonObject = JSONObject(result)
                        val itemsArray = jsonObject.getJSONArray("items")

                        val newList = mutableListOf<SearchInfo>()

                        for (i in 0 until itemsArray.length()) {
                            val book = itemsArray.getJSONObject(i)
                            val volumeInfo = book.getJSONObject("volumeInfo")
                            val title = volumeInfo.getString("title")
                            val authorsArray = volumeInfo.optJSONArray("authors")
                            val author = authorsArray?.getString(0) ?: "Unknown Author"
                            val imageUrl = volumeInfo.optJSONObject("imageLinks")?.getString("thumbnail") ?: ""
                            // Create a new SearchInfo object and add it to the new list
                            newList.add(SearchInfo(title, author,imageUrl))
                        }

                        // Update the value of bookApi with the new list
                        bookApi.value = newList
                    } catch (e: Exception) {
                        Log.e("BOOK", e.message.toString())
                        Log.e("BOOK", urlString)
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Log.e("BOOK", error.toString())
                    Log.e("BOOK", urlString)
                }
            })
        }
    }

    fun setBook(book: String) {
        this.book.value = book
        Log.d("BOOK", this.book.value)
    }

    fun resetUI() {
        UIState = AddUiState()
    }

    fun setAuthor(it: String) {
        var currentUI = UIState
        UIState = currentUI.copy(author = it)
    }

    fun setName(it: String) {
        var currentUI = UIState
        UIState = currentUI.copy(name = it)
    }

    fun setPages(it: String) {
        var currentUI = UIState
        UIState = currentUI.copy(pages = it)
    }

    fun setExpectation(it: String) {
        var currentUI = UIState
        UIState = currentUI.copy(expectation = it)
    }

    fun setPhoto(uri: Uri) {
        try {
            val compressedImage = compressImage(uri)
            UIState = UIState.copy(ImageStr = compressedImage)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun compressImage(uri: Uri): ByteArray? {
        return try {
            val inputStream = app.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust quality as needed
            outputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun AddBook()= bookRepository.AddBook(UIState.toBook())

}

data class SearchInfo(val title:String,val author: String,val imageUrl: String)
