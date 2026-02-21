package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.data.remote.api.AttractionApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.CategoryApiService
import me.marthia.app.boomgrad.data.remote.api.CategoryApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.GuideApiService
import me.marthia.app.boomgrad.data.remote.api.GuideApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.LoginApiService
import me.marthia.app.boomgrad.data.remote.api.LoginApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.ProfileApiService
import me.marthia.app.boomgrad.data.remote.api.ProfileApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.ProvinceCityService
import me.marthia.app.boomgrad.data.remote.api.ProvinceCityServiceImpl
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.data.remote.api.TourApiServiceImpl
import me.marthia.app.boomgrad.data.remote.repository.AttractionRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.CategoryRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.GuideRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.LoginRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.ProfileRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.ProvinceRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.TourRepositoryImpl
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.repository.CategoryRepository
import me.marthia.app.boomgrad.domain.repository.GuideRepository
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.domain.repository.ProfileRepository
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository
import me.marthia.app.boomgrad.domain.repository.TourRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// Repository Module
val repositoryModule = module {

    singleOf(::AttractionApiServiceImpl) bind AttractionApiService::class
    single<AttractionRepository> { AttractionRepositoryImpl(get(), androidContext()) }

    single<TokenManager> { TokenManager(androidContext()) }

    // login repo and service
    singleOf(::LoginApiServiceImpl) bind LoginApiService::class
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class


    singleOf(::TourRepositoryImpl) bind TourRepository::class
    singleOf(::TourApiServiceImpl) bind TourApiService::class

    singleOf(::ProvinceRepositoryImpl) bind ProvinceCityRepository::class
    singleOf(::ProvinceCityServiceImpl) bind ProvinceCityService::class

    singleOf(::ProfileApiServiceImpl) bind ProfileApiService::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class

    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    singleOf(::CategoryApiServiceImpl) bind CategoryApiService::class

    singleOf(::GuideApiServiceImpl) bind GuideApiService::class
    singleOf(::GuideRepositoryImpl) bind GuideRepository::class
}