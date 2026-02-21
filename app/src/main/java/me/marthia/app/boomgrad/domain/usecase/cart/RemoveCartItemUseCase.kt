package me.marthia.app.boomgrad.domain.usecase.cart


import me.marthia.app.boomgrad.domain.repository.CartRepository


class RemoveCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(itemId: Long): Result<Unit> =
        repository.removeItem(itemId)
}
