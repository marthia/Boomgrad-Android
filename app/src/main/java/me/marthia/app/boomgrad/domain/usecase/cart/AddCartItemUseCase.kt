package me.marthia.app.boomgrad.domain.usecase.cart

import me.marthia.app.boomgrad.domain.model.CartItem
import me.marthia.app.boomgrad.domain.repository.CartRepository

class AddCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(
        tourId: Long,
        scheduleId: Long,
        peopleCount: Int,
    ): Result<CartItem> = repository.addItem(tourId, scheduleId, peopleCount)
}