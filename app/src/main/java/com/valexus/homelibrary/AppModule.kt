package com.valexus.homelibrary

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.valexus.homelibrary.data.repositories.AuthRepository
import com.valexus.homelibrary.data.repositories.AuthRepositoryImpl
import com.valexus.homelibrary.data.repositories.BookRepository
import com.valexus.homelibrary.data.repositories.BookRepositoryImpl
import com.valexus.homelibrary.data.storage.BookDao
import com.valexus.homelibrary.data.storage.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideBookRepository(impl: BookRepositoryImpl): BookRepository = impl

    @Provides
    fun provideBookDatabase(@ApplicationContext appContext:Context): BookDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            BookDatabase::class.java,
            "books_database"
        ).build()
    }

    @Provides
    fun provideBookDao(database: BookDatabase): BookDao = database.bookDao()

}