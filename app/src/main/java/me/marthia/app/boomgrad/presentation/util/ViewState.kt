package me.marthia.app.boomgrad.presentation.util

sealed interface ViewState<out T> {

    data object Loading : ViewState<Nothing>

    data object Empty : ViewState<Nothing>

    data class Data<T>(val value: T) : ViewState<T>

    data class Error(val throwable: Throwable) : ViewState<Nothing>
}