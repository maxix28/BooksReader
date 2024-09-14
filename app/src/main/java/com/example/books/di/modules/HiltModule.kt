package com.example.books.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.books.data.BookRepository
import com.example.books.data.DefaultContainer
import com.example.books.database.BookDao
import com.example.books.database.BooksDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

}

