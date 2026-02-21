package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AddCartItemRequestDto
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.CartItemDto
import me.marthia.app.boomgrad.data.remote.dto.CartResponseDto
import me.marthia.app.boomgrad.data.remote.dto.ConfirmCartPaymentRequestDto
import me.marthia.app.boomgrad.data.remote.dto.UpdateCartItemRequestDto

interface CartApiService {
    suspend fun getCart(): Result<BaseResponse<CartResponseDto>>
    suspend fun addItem(request: AddCartItemRequestDto): Result<BaseResponse<CartItemDto>>
    suspend fun updateItem(
        itemId: Long,
        request: UpdateCartItemRequestDto
    ): Result<BaseResponse<CartItemDto>>

    suspend fun removeItem(itemId: Long): Result<BaseResponse<Unit>>
    suspend fun checkout(): Result<BaseResponse<CartResponseDto>>
    suspend fun confirmPayment(request: ConfirmCartPaymentRequestDto): Result<BaseResponse<Unit>>
}