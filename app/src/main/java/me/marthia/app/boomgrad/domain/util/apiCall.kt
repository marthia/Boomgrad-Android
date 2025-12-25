package me.marthia.app.boomgrad.domain.util

import me.marthia.app.boomgrad.presentation.util.DataState
import me.marthia.app.boomgrad.presentation.util.handleThrowable
import timber.log.Timber
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T : Any> apiCall(call: suspend () -> T): DataState<T> {
    return try {
        val response = call()
        DataState.Success(response)
    } catch (ex: Throwable) {
        DataState.Error(ex.handleThrowable())
    }
}