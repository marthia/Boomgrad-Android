package me.marthia.app.boomgrad.domain.usecase.login

import kotlinx.coroutines.flow.FlowCollector
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.domain.util.DataStateUseCase
import me.marthia.app.boomgrad.presentation.util.DataState
import me.marthia.app.boomgrad.domain.util.apiCall

class LoginUseCase (
    val repo: LoginRepository
) : DataStateUseCase<LoginUseCase.LoginParams, String>() {

    data class LoginParams(
        val phone: String
    )

    override suspend fun FlowCollector<DataState<String>>.execute(params: LoginParams) {

        val service = apiCall { repo.login(params.phone) }

        emit(service.map { it.message!! })
    }
}