package me.marthia.app.boomgrad.domain.usecase.cart

import me.marthia.app.boomgrad.domain.model.CartItem
import me.marthia.app.boomgrad.domain.repository.CartRepository

class UpdateCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(
        itemId: Long,
        peopleCount: Int,
    ): Result<CartItem> = repository.updateItem(itemId, peopleCount)
}