package com.example.books.di.modules

import android.app.Application
import com.example.books.data.BookRepository
import com.example.books.data.DefaultContainer
import com.example.books.database.BookDao
import com.example.books.database.BooksDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
@Provides
fun provideBookDao(app:Application):BookDao{
    return BooksDataBase.getDataBase(app).bookDao()
}


    @Provides
    fun provideBookRepo(bookDao: BookDao):BookRepository{
        return DefaultContainer(bookDao)
    }

}

