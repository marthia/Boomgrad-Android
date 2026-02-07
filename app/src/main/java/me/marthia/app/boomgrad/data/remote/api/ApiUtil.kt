package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure

// network/KtorExtensions.kt
suspend inline fun <reified T> HttpClient.safeGet(
    urlString: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = get(urlString) {
            block()
        }.body<T>()
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e.toNetworkFailure())
    }
}

suspend inline fun <reified T> HttpClient.safePost(
    urlString: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = post(urlString) {
            block()
        }.body<T>()
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e.toNetworkFailure())
    }
}

suspend inline fun <reified T> HttpClient.safePut(
    urlString: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = put(urlString) {
            block()
        }.body<T>()
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e.toNetworkFailure())
    }
}

suspend inline fun <reified T> HttpClient.safeDelete(
    urlString: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = delete(urlString) {
            block()
        }.body<T>()
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e.toNetworkFailure())
    }
}