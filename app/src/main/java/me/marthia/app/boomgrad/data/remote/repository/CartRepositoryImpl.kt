package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.CartApiService
import me.marthia.app.boomgrad.data.remote.dto.AddCartItemRequestDto
import me.marthia.app.boomgrad.data.remote.dto.ConfirmCartPaymentRequestDto
import me.marthia.app.boomgrad.data.remote.dto.UpdateCartItemRequestDto
import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.model.CartItem
import me.marthia.app.boomgrad.domain.repository.CartRepository

class CartRepositoryImpl(
    private val api: CartApiService
) : CartRepository {

    override suspend fun getCart(): Result<Cart> =
        runCatching {
            api.getCart().getOrThrow()
        }.map { it.data.toDomain() }

    override suspend fun addItem(
        tourId: Long,
        scheduleId: Long,
        peopleCount: Int,
    ): Result<CartItem> =
        runCatching {
            api.addItem(
                AddCartItemRequestDto(
                    tourId = tourId,
                    scheduleId = scheduleId,
                    peopleCount = peopleCount,
                )
            ).getOrThrow()
        }.map { it.data.toDomain() }

    override suspend fun updateItem(
        itemId: Long,
        peopleCount: Int,
    ): Result<CartItem> =
        runCatching {
            api.updateItem(
                itemId = itemId,
                request = UpdateCartItemRequestDto(peopleCount = peopleCount),
            ).getOrThrow()
        }.map { it.data.toDomain() }

    override suspend fun removeItem(itemId: Long): Result<Unit> =
        runCatching {
            api.removeItem(itemId).getOrThrow()
        }

    override suspend fun checkout(): Result<Cart> =
        runCatching {
            api.checkout().getOrThrow()
        }.map { it.data.toDomain() }

    override suspend fun confirmPayment(
        paymentId: String,
        totalAmount: Long,
    ): Result<Unit> =
        runCatching {
            api.confirmPayment(
                ConfirmCartPaymentRequestDto(
                    paymentId = paymentId,
                    totalAmount = totalAmount,
                )
            ).getOrThrow()
        }
}