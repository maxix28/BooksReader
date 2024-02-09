package com.example.books.ui.screens

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.books.data.BookRepository
import com.example.books.database.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
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

object ImageUtils {

    // Method to compress the bitmap
    fun compressBitmap(context: Context, uri: String): ByteArray? {
        val bitmap = decodeUri(context, uri)
        val compressedBitmap = compressImage(bitmap)
        return bitmapToByteArray(compressedBitmap)
    }

    // Method to decode the image from URI into a Bitmap
    private fun decodeUri(context: Context, uri: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val inputStream = FileInputStream(uri)
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    // Method to compress the image
    private fun compressImage(image: Bitmap?): Bitmap? {
        val outputStream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust quality as needed
        return BitmapFactory.decodeStream(outputStream.toByteArray().inputStream())
    }

    // Method to convert Bitmap to byte array
    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
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
//    fun setPhoto(it: Uri) {
//        var curentUI = UIState
//       // UIState = curentUI.copy(ImageStr = it)
//        try {
//
//            val newImage = app.contentResolver.openInputStream(it)?.readBytes()
//            newImage?.let {
//                UIState = curentUI.copy(ImageStr = it)
//                println(UIState)
//
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }
//
//    }

    fun setPhoto(uri: Uri) {
        try {
            val compressedImage = compressImage(uri)
            UIState = UIState.copy(ImageStr = compressedImage)
            println(UIState)
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