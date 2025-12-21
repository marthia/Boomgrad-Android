package me.marthia.app.boomgrad.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A base class for ViewModels in an MVVM architecture, providing common functionality
 * for handling coroutines, asynchronous operations, and error management.
 *
 * This class simplifies common tasks such as:
 * - Launching coroutines safely with a built-in exception handler (`safeLaunch`).
 * - Handling loading states (`startLoading`).
 * - Collecting `Flow` streams with standardized error handling (`call`, `execute`).
 *
 * Subclasses should extend this class to inherit this boilerplate-reducing functionality
 * and can override `handleError` and `startLoading` to implement custom logic, such as
 * updating UI state to show error messages or loading indicators.
 */
abstract class MvvmViewModel : ViewModel() {

    /**
     * A [CoroutineExceptionHandler] that logs the exception using Timber and then delegates
     * the handling to the [handleError] function. This is used by [safeLaunch] to ensure
     * that any uncaught exceptions within the coroutine are properly managed.
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(SAFE_LAUNCH_EXCEPTION).e(exception)
        handleError(exception)
    }

    open fun handleError(exception: Throwable) {}

    open fun startLoading() {

    }

    /**
     * Launches a coroutine in the `viewModelScope` with a built-in exception handler.
     * Any uncaught exception thrown within the coroutine block will be caught by the
     * [CoroutineExceptionHandler] and passed to the `handleError` function.
     *
     * This is a convenient way to launch fire-and-forget coroutines (e.g., for database writes)
     * without needing to wrap every call in a try-catch block.
     *
     * @param block The suspendable block of code to be executed in the coroutine.
     */
    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    protected suspend fun <T> execute(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .onStart { startLoading() }
            .catch { handleError(it) }
            .collect { state ->
                when (state) {
                    is DataState.Error -> handleError(state.error)
                    is DataState.Success -> completionHandler.invoke(state.result)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()

        Timber.d("Cleared")
    }

    companion object {
        private const val SAFE_LAUNCH_EXCEPTION = "ViewModel-Exception"
    }
}