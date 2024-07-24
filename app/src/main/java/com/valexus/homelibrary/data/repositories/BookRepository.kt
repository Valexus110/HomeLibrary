package com.valexus.homelibrary.data.repositories

import androidx.lifecycle.LiveData
import com.valexus.homelibrary.data.models.Book

interface BookRepository {
    fun getBook(bookId:Long)
    fun getAllBooks(): LiveData<List<Book>>
    fun addBook()
    fun updateBook()
    fun deleteBook()
}