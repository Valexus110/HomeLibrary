package com.valexus.homelibrary.data

sealed class NetworkState<out R> {
    data class Success<out R>(val result: R) : NetworkState<R>()
    data class Failure(val exception: Exception) : NetworkState<Nothing>()
    data object Loading : NetworkState<Nothing>()
}