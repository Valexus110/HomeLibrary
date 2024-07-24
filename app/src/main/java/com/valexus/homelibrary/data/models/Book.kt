package com.valexus.homelibrary.data.models

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valexus.homelibrary.R

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Long = 0L,
    val title: String,
    val author: String,
    val genre: String,
    val cover: Int = R.drawable.ic_launcher_background,
    @ColumnInfo(name = "number_of_pages")
    val numberOfPages: Int,
    @ColumnInfo(name = "year_of_publication")
    val yearOfPublication: Int,
)
