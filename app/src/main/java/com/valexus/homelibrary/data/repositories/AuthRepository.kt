package com.valexus.homelibrary.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.valexus.homelibrary.data.NetworkState
import com.valexus.homelibrary.data.models.User

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(user:User): NetworkState<FirebaseUser>
    suspend fun signup(user:User): NetworkState<FirebaseUser>
    fun logout()
}