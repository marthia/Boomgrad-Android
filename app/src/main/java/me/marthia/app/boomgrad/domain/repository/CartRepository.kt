package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.model.CartItem

interface CartRepository {
    suspend fun getCart(): Result<Cart>
    suspend fun addItem(tourId: Long, scheduleId: Long, peopleCount: Int): Result<CartItem>
    suspend fun updateItem(itemId: Long, peopleCount: Int): Result<CartItem>
    suspend fun removeItem(itemId: Long): Result<Unit>
    suspend fun checkout(): Result<Cart>             // creates PENDING bookings, returns total
    suspend fun confirmPayment(paymentId: String, totalAmount: Long): Result<Unit>
}