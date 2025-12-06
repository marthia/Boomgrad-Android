package me.marthia.app.boomgrad.di


import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.withCharset
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber

object ApiConfig {
    const val HOST = "10.0.2.2"
    const val PORT = 8080
    const val BASE_PATH = "api/"
    const val TIMEOUT_MILLIS = 30_000L
    const val VERSION = "1.0.0"
}

val networkModule = module {
    // JSON Configuration
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
            coerceInputValues = true
            useAlternativeNames = true
        }
    }

    // HttpClient Configuration
    single<HttpClient> {
        HttpClient(CIO) {
            // Content Negotiation for JSON
            install(ContentNegotiation) {
                json(get<Json>())
            }

            // Logging Plugin
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("KtorClient$message")
                    }
                }
                level = LogLevel.ALL
            }

            // Response Observer
            install(ResponseObserver) {
                onResponse { response ->
                    Timber.d("HTTP Response${response.status.value}")
                }
            }


            /**
             * Default Request Configuration.
             * This block allows setting up default parameters for every outgoing request
             * made by this HttpClient instance. This is useful for adding common headers,
             * base URLs, or query parameters.
             */
            defaultRequest {

                // Configure the base URL for all requests
                url {
                    // Set the protocol (e.g., HTTP or HTTPS)
                    protocol = URLProtocol.HTTP // Or URLProtocol.HTTPS if needed
                    // Set the base host for the API
                    host = ApiConfig.HOST
                    port = ApiConfig.PORT
                    // Prepend the base path for all API endpoints
                    path(ApiConfig.BASE_PATH)

                    // Automatically append version and token as query parameters to every request
                    parameters.append("version", ApiConfig.VERSION)
//                    TokenManager(androidContext()).getToken()?.let { token ->
//                        parameters.append("token", token)
//                    }
                }
                // Set the default Content-Type header for all requests to application/json
                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))

            }

            // Engine Configuration
            engine {
                requestTimeout = ApiConfig.TIMEOUT_MILLIS
                endpoint {
                    connectTimeout = ApiConfig.TIMEOUT_MILLIS
                    socketTimeout = ApiConfig.TIMEOUT_MILLIS
                }
            }
        }
    }

}