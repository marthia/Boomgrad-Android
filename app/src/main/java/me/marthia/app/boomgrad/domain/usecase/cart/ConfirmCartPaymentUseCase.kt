package me.marthia.app.boomgrad.domain.usecase.cart

import me.marthia.app.boomgrad.domain.repository.CartRepository

class ConfirmCartPaymentUseCase(private val repository: CartRepository) {
    /**
     * Called after Zarinpal redirects back successfully.
     * Sends paymentId + totalAmount to backend for verification,
     * which atomically confirms all PENDING bookings and clears the cart.
     */
    suspend operator fun invoke(
        paymentId: String,
        totalAmount: Long,
    ): Result<Unit> = repository.confirmPayment(paymentId, totalAmount)
}