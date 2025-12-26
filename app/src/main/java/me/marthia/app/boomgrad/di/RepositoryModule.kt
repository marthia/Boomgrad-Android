package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.remote.api.LoginApiService
import me.marthia.app.boomgrad.data.remote.api.LoginApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.data.remote.api.TourApiServiceImpl
import me.marthia.app.boomgrad.data.remote.repository.AttractionRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.LoginRepositoryImpl
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.login.ClearTokenUseCase
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// Repository Module
val repositoryModule = module {

    singleOf(::TourApiServiceImpl) bind TourApiService::class
    singleOf(::AttractionRepositoryImpl) bind AttractionRepository::class

    single<TokenManager> { TokenManager(androidContext()) }

    // UseCases
    single<GetAttractionsUseCase> { GetAttractionsUseCase(get()) }
    single<GetAttractionDetailUseCase> { GetAttractionDetailUseCase(get()) }
    single<LoginUseCase> { LoginUseCase(get()) }
    single<ClearTokenUseCase> { ClearTokenUseCase(get()) }

    // login repo and service
    singleOf(::LoginApiServiceImpl) bind LoginApiService::class
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
}