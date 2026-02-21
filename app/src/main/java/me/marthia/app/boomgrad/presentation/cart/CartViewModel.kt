package me.marthia.app.boomgrad.presentation.cart

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.cart.GetCartUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.RemoveCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.UpdateCartItemUseCase
import me.marthia.app.boomgrad.presentation.cart.model.CartItemUiModel
import me.marthia.app.boomgrad.presentation.cart.model.CartUiMapper
import me.marthia.app.boomgrad.presentation.cart.model.CartUiModel
import me.marthia.app.boomgrad.presentation.util.ViewState
import java.util.Locale

class CartViewModel(
    private val getCart: GetCartUseCase,
    private val removeCartItem: RemoveCartItemUseCase,
    private val updateCartItem: UpdateCartItemUseCase,
    private val locale: Locale,          // injected via Koin — Locale("fa", "IR") or Locale.ENGLISH
) : ViewModel() {

    private val mapper = CartUiMapper(locale)

    // ── UI state ──────────────────────────────────────────────────────────────
    private val _uiState = MutableStateFlow<ViewState<CartUiModel>>(ViewState.Loading)
    val uiState: StateFlow<ViewState<CartUiModel>> = _uiState.asStateFlow()

    /**
     * Holds a MutableIntState per cart item so QuantitySelector can bind directly.
     * Key = cartItemId
     */
    private val _peopleCounts = mutableMapOf<Long, androidx.compose.runtime.MutableIntState>()

    init {
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            try {
                val cart = getCart().getOrNull()
                // In a real app, also fetch availability warnings here
                val uiModel = mapper.mapCart(cart!!, emptyMap())

                // Seed quantity states for new items; preserve existing ones so
                // the selector doesn't reset while the user is tapping +/-
                uiModel.items.forEach { item ->
                    _peopleCounts.getOrPut(item.id) {
                        mutableIntStateOf(item.peopleCount)
                    }
                }
                // Remove stale keys for items no longer in cart
                _peopleCounts.keys.retainAll(uiModel.items.map { it.id }.toSet())

                _uiState.value = ViewState.Success(uiModel)
            } catch (e: Exception) {
                _uiState.value = ViewState.Error(e)
            }
        }
    }

    /**
     * Returns the MutableIntState for a given cart item.
     * Composables bind to this directly — no extra state hoisting needed.
     */
    fun peopleCountFor(itemId: Long): androidx.compose.runtime.MutableIntState {
        return _peopleCounts.getOrPut(itemId) { mutableIntStateOf(1) }
    }

    fun onPeopleCountChanged(item: CartItemUiModel, newCount: Int) {
        if (newCount < 1 || newCount > item.maxPeople) return
        viewModelScope.launch {
            try {
                updateCartItem(item.id, newCount)
                // Recalculate total price label for this item and refresh cart summary
                loadCart()
            } catch (e: Exception) {
                // Optionally show a snackbar; revert the count state
                _peopleCounts[item.id]?.intValue = item.peopleCount
            }
        }
    }

    fun onRemoveItem(itemId: Long) {
        viewModelScope.launch {
            try {
                removeCartItem(itemId)
                _peopleCounts.remove(itemId)
                loadCart()
            } catch (e: Exception) {
                _uiState.value = ViewState.Error(e)
            }
        }
    }

    fun onCheckout() {
        // Navigate to payment — pass totalAmountToman to Zarinpal
        val state = _uiState.value as? ViewState.Success ?: return
//        val total = state.cart.totalAmountToman
        // TODO: trigger navigation event with total
    }

    // ── Price formatting exposed to Composables ───────────────────────────────
    fun formatPrice(amount: Long): String = mapper.formatPrice(amount)
}