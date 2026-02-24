package me.marthia.app.boomgrad.data.remote.image

import coil.intercept.Interceptor
import coil.request.ImageResult
import timber.log.Timber

class BaseUrlInterceptor(private val basePath: String) : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request
        val data = request.data.toString()


        return if (shouldPrependPath(data)) {
            val imageUrl = "$basePath$data"
            Timber.i("loading image using coil: $imageUrl")

            val newRequest = request.newBuilder()
                .data(imageUrl)
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }

    private fun shouldPrependPath(data: String): Boolean {
        // If it's a relative path (doesn't start with http, content, or android)
        val digitsRegex = Regex("\\d+")
        return !digitsRegex.matches(data) &&
                !data.startsWith("http") &&
                !data.startsWith("content") &&
                !data.startsWith("android.resource") &&
                data.isNotBlank()
    }
}