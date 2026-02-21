package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.marthia.app.boomgrad.data.remote.dto.AddCartItemRequestDto
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.CartItemDto
import me.marthia.app.boomgrad.data.remote.dto.CartResponseDto
import me.marthia.app.boomgrad.data.remote.dto.ConfirmCartPaymentRequestDto
import me.marthia.app.boomgrad.data.remote.dto.UpdateCartItemRequestDto

class CartApiServiceImpl(
    private val client: HttpClient
) : CartApiService {

    override suspend fun getCart(): Result<BaseResponse<CartResponseDto>> =
        client.safeGet("cart")

    override suspend fun addItem(
        request: AddCartItemRequestDto
    ): Result<BaseResponse<CartItemDto>> =
        client.safePost("cart/items") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

    override suspend fun updateItem(
        itemId: Long,
        request: UpdateCartItemRequestDto
    ): Result<BaseResponse<CartItemDto>> =
        client.safePatch("cart/items/$itemId") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

    override suspend fun removeItem(
        itemId: Long
    ): Result<BaseResponse<Unit>> =
        client.safeDelete("cart/items/$itemId")

    override suspend fun checkout(): Result<BaseResponse<CartResponseDto>> =
        client.safePost("cart/checkout")

    override suspend fun confirmPayment(
        request: ConfirmCartPaymentRequestDto
    ): Result<BaseResponse<Unit>> =
        client.safePost("cart/confirm") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
}