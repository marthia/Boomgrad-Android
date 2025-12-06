package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.data.AttractionRepositoryImpl
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.data.remote.api.TourApiServiceImpl
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.usecase.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.GetAttractionsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// Repository Module
val repositoryModule = module {

    singleOf(::TourApiServiceImpl) bind TourApiService::class
    singleOf(::AttractionRepositoryImpl) bind AttractionRepository::class

    // UseCases
    single<GetAttractionsUseCase> { GetAttractionsUseCase(get()) }
    single<GetAttractionDetailUseCase> { GetAttractionDetailUseCase(get()) }
}