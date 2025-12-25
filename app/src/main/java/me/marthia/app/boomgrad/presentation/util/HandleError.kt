package me.marthia.app.boomgrad.presentation.util


import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import me.marthia.app.boomgrad.domain.util.HttpStatusCode
import timber.log.Timber
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import javax.net.ssl.SSLException


sealed class Failure : IOException() {
    abstract val originalException: Throwable?


    data class NetworkError(override val originalException: Throwable? = null) : Failure()


    data class TimeoutError(override val originalException: Throwable? = null) : Failure()


    data class ClientError(
        val httpStatusCode: HttpStatusCode,
        override val originalException: Throwable? = null
    ) : Failure()


    data class ServerError(
        val httpStatusCode: HttpStatusCode,
        override val originalException: Throwable? = null
    ) : Failure()


    data class ParseError(override val originalException: Throwable? = null) : Failure()


    data class UnknownError(override val originalException: Throwable? = null) : Failure()
}

fun Throwable.handleThrowable(): Failure {
    return when (this) {
        is UnknownHostException -> {
            Timber.e(this, "Network error: No internet connection")
            Failure.NetworkError(originalException = this)
        }

        is ConnectException -> {
            Timber.e(this, "Network error: Connection failed")
            Failure.NetworkError(originalException = this)
        }

        is SocketTimeoutException -> {
            Timber.e(this, "Timeout error: Request timed out")
            Failure.TimeoutError(originalException = this)
        }

        is InterruptedIOException -> {
            Timber.e(this, "Timeout error: Request interrupted")
            Failure.TimeoutError(originalException = this)
        }

        is HttpRequestTimeoutException -> {
            Timber.e(this, "Timeout error: HTTP request timed out")
            Failure.TimeoutError(originalException = this)
        }

        is ResponseException -> {
            val statusCode = HttpStatusCode.fromCode(response.status.value)
            Timber.e("HTTP error: ${statusCode.name} (${response.status.value})")
            when (response.status.value) {
                in 400..499 -> Failure.ClientError(statusCode, this)
                in 500..599 -> Failure.ServerError(statusCode, this)
                else -> Failure.UnknownError(this)
            }
        }

        is JsonConvertException,
        is SerializationException -> {
            Timber.e(this, "Parse error: Failed to parse response - ${message}")
            Failure.ParseError(originalException = this)
        }

        is SSLException,
        is CertificateException -> {
            Timber.e(this, "Security error: SSL/Certificate error - ${message}")
            Failure.NetworkError(originalException = this)
        }

        is IOException -> {
            Timber.e(this, "IO error: ${message}")
            Failure.NetworkError(originalException = this)
        }

        else -> {
            Timber.e(this, "Unknown error: ${this::class.simpleName} - ${message}")
            Failure.UnknownError(originalException = this)
        }
    }
}
