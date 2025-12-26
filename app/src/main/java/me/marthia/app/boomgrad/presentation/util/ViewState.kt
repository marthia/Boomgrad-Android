package me.marthia.app.boomgrad.presentation.util

sealed interface ViewState<out T> {

    data object Loading : ViewState<Nothing>

    data object Idle : ViewState<Nothing>

    data class Success<T>(val value: T) : ViewState<T>

    data class Error(val throwable: Throwable) : ViewState<Nothing>
}