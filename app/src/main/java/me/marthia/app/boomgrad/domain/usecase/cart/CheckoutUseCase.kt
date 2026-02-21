package me.marthia.app.boomgrad.domain.usecase.cart

import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.repository.CartRepository

class CheckoutUseCase(private val repository: CartRepository) {
    /**
     * Converts cart items to PENDING bookings on the backend.
     * Returns the Cart with the final total to pass to Zarinpal.
     */
    suspend operator fun invoke(): Result<Cart> = repository.checkout()
}