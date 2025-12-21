package me.marthia.app.boomgrad.domain.util

abstract class ReturnUseCase<in Params, ReturnType> where ReturnType : Any? {

    protected abstract suspend fun execute(params: Params): ReturnType

    suspend operator fun invoke(params: Params): ReturnType = execute(params)
}