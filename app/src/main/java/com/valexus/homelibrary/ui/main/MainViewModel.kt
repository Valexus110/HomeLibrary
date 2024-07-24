package com.valexus.homelibrary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valexus.homelibrary.data.models.Book
import com.valexus.homelibrary.data.repositories.BookRepository
import com.valexus.homelibrary.data.storage.BookDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(bookRepository: BookRepository): ViewModel() {
    var books = bookRepository.getAllBooks() //MutableLiveData<List<Book>>()
}