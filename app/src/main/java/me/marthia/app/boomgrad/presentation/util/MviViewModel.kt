package me.marthia.app.boomgrad.presentation.util


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A base ViewModel class that implements the Model-View-Intent (MVI) architectural pattern.
 * It manages the state of the UI and handles events triggered by the user.
 *
 * This class is designed to be extended by specific ViewModels in the application.
 *
 * @param STATE The type of the state object, which must extend [ViewState].
 *              Represents the state of the UI at any given moment.
 * @param EVENT The type of the event object. Represents user actions or other
 *              events that can trigger a state change.
 */
abstract class MviViewModel<STATE : ViewState<*>, EVENT> : MvvmViewModel() {

    private val _uiState = MutableStateFlow<ViewState<*>>(ViewState.Loading)
    val uiState = _uiState.asStateFlow()

    /**
     * Function that trigger events.
     * @param eventType The event type to be triggered.
     */
    abstract fun onTriggerEvent(eventType: EVENT)

    /**
     * Sets the new state of the view.
     * This function should be called from the ViewModel to update the UI state.
     * It emits the new state to the `uiState` flow, which observers in the UI layer can collect.
     * The operation is performed within a `safeLaunch` coroutine block to handle potential exceptions.
     *
     * @param state The new state to be set for the view.
     */
    protected fun setState(state: STATE) = safeLaunch {
        _uiState.emit(state)
    }

    override fun startLoading() {
        super.startLoading()
        _uiState.value = ViewState.Loading
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        _uiState.value = ViewState.Error(exception)
    }
}