package me.marthia.app.boomgrad.domain.usecase.cart

import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.repository.CartRepository

class GetCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(): Result<Cart> = repository.getCart()
}