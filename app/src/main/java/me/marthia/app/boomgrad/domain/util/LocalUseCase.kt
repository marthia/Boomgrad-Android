package me.marthia.app.boomgrad.domain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class LocalUseCase<in Params, ReturnType> where ReturnType : Any {

    protected abstract suspend fun execute(params: Params)

    suspend operator fun invoke(params: Params) = withContext(Dispatchers.IO) {
        execute(params)
    }
}