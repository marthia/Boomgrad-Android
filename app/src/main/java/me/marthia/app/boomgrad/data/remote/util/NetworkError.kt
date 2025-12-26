package me.marthia.app.boomgrad.data.remote.util

import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import javax.net.ssl.SSLException


sealed class NetworkFailure : IOException() {
    abstract val originalException: Throwable?

    data class NetworkError(override val originalException: Throwable? = null) : NetworkFailure()
    data class TimeoutError(override val originalException: Throwable? = null) : NetworkFailure()
    data class ClientError(
        val httpStatusCode: HttpStatusCode,
        override val originalException: Throwable? = null
    ) : NetworkFailure()

    data class ServerError(
        val httpStatusCode: HttpStatusCode,
        override val originalException: Throwable? = null
    ) : NetworkFailure()

    data class ParseError(override val originalException: Throwable? = null) : NetworkFailure()
    data class UnknownError(override val originalException: Throwable? = null) : NetworkFailure()
}


fun Throwable.toNetworkFailure(): NetworkFailure {
    return when (this) {
        is UnknownHostException -> {
            Timber.e(this, "Network error: No internet connection")
            NetworkFailure.NetworkError(originalException = this)
        }

        is ConnectException -> {
            Timber.e(this, "Network error: Connection failed")
            NetworkFailure.NetworkError(originalException = this)
        }

        is SocketTimeoutException -> {
            Timber.e(this, "Timeout error: Request timed out")
            NetworkFailure.TimeoutError(originalException = this)
        }

        is InterruptedIOException -> {
            Timber.e(this, "Timeout error: Request interrupted")
            NetworkFailure.TimeoutError(originalException = this)
        }

        is HttpRequestTimeoutException -> {
            Timber.e(this, "Timeout error: HTTP request timed out")
            NetworkFailure.TimeoutError(originalException = this)
        }

        is ResponseException -> {
            val statusCode = HttpStatusCode.fromValue(response.status.value)
            Timber.e("HTTP error: ${statusCode.description} (${response.status.value})")
            when (response.status.value) {
                in 400..499 -> NetworkFailure.ClientError(
                    httpStatusCode = statusCode,
                    originalException = this
                )

                in 500..599 -> NetworkFailure.ServerError(
                    httpStatusCode = statusCode,
                    originalException = this
                )

                else -> NetworkFailure.UnknownError(this)
            }
        }

        is JsonConvertException,
        is SerializationException -> {
            Timber.e(this, "Parse error: Failed to parse response - ${message}")
            NetworkFailure.ParseError(originalException = this)
        }

        is SSLException,
        is CertificateException -> {
            Timber.e(this, "Security error: SSL/Certificate error - ${message}")
            NetworkFailure.NetworkError(originalException = this)
        }

        is java.io.IOException -> {
            Timber.e(this, "IO error: ${message}")
            NetworkFailure.NetworkError(originalException = this)
        }

        else -> {
            Timber.e(this, "Unknown error: ${this::class.simpleName} - ${message}")
            NetworkFailure.UnknownError(originalException = this)
        }
    }
}