package me.marthia.app.boomgrad.presentation.util


import android.net.http.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Failure : IOException() {
    object UnknownError : Failure()
    object ClientError : Failure()
    object ServerError : Failure()
}

fun Throwable.handleThrowable(): Failure {
    Timber.e(this)
    return if (this is UnknownHostException) {
        Failure.ClientError
    } else if (this is HttpException) {
        Failure.ServerError
    } else if (this is SocketTimeoutException) {
        Failure.ServerError
    } else if (this.message != null) {
        Failure.ServerError
    } else {
        Failure.UnknownError
    }
}