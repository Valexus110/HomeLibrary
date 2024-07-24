package com.valexus.homelibrary.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.valexus.homelibrary.data.NetworkState
import com.valexus.homelibrary.data.models.User
import com.valexus.homelibrary.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    companion object {
        const val TAG: String = "AuthViewModel"
    }

    private var user = MutableLiveData<User>(null)

    private val _networkState = MutableLiveData<NetworkState<FirebaseUser>?>(null)
    val networkState: LiveData<NetworkState<FirebaseUser>?> = _networkState

    private val loginFlow = MutableStateFlow<NetworkState<FirebaseUser>?>(null)

    private val signupFlow = MutableStateFlow<NetworkState<FirebaseUser>?>(null)

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            loginFlow.value = NetworkState.Success(repository.currentUser!!)
        }
        manageLoginState()
        manageSignupState()
    }

    private fun manageLoginState() = viewModelScope.launch {
        loginFlow.collect {
            _networkState.value = it
        }
    }

    private fun manageSignupState() = viewModelScope.launch {
        signupFlow.collect {
            _networkState.value = it
        }
    }

    fun setUser(email: String, password: String, username: String? = null) {
        user.value = User(email = email, password = password, username = username ?: "")
    }

    fun loginUser() = viewModelScope.launch {
        Log.i(TAG, "${user.value}")
        user.value?.let {
            loginFlow.value = NetworkState.Loading
            val result = repository.login(it)
            loginFlow.value = result
        }
    }

    fun signupUser() = viewModelScope.launch {
        user.value?.let {
            signupFlow.value = NetworkState.Loading
            val result = repository.signup(it)
            signupFlow.value = result
        }
    }

    fun clearFlows() {
        loginFlow.value = null
        signupFlow.value = null
    }

    fun logout() {
        repository.logout()
        clearFlows()
    }
}