package com.valexus.homelibrary.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.valexus.homelibrary.data.NetworkState
import com.valexus.homelibrary.data.await
import com.valexus.homelibrary.data.models.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(user: User): NetworkState<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
            NetworkState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkState.Failure(e)
        }
    }

    override suspend fun signup(
        user: User
    ): NetworkState<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(user.username).build()
            )?.await()
            return NetworkState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkState.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}