package com.valexus.homelibrary.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valexus.homelibrary.data.models.Book

@Database(entities = [Book::class],version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao() : BookDao

    companion object {}
}
