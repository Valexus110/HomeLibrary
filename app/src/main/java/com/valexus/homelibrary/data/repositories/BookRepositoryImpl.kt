package com.valexus.homelibrary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.valexus.homelibrary.data.models.Book
import com.valexus.homelibrary.data.storage.BookDao
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BookDao) : BookRepository {

    init {

    }
    companion object {
        private var book = MutableLiveData<Book?>(null)
    }

    override fun getBook(bookId:Long) {
        bookDao.get(bookId)
    }

    override fun getAllBooks(): LiveData<List<Book>> {
        return bookDao.getAll()
    }

    override fun addBook() {
        TODO("Not yet implemented")
    }

    override fun updateBook() {
        TODO("Not yet implemented")
    }

    override fun deleteBook() {
        TODO("Not yet implemented")
    }
}