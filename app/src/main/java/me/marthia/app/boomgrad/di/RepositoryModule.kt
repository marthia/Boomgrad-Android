package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.data.remote.api.AttractionApiServiceImpl
import me.marthia.app.boomgrad.data.remote.api.CategoryApiService
import me.marthia.app.boomgrad.data.remote.api.CategoryApiServiceImpl
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
import me.marthia.app.boomgrad.data.remote.repository.LoginRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.ProfileRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.ProvinceRepositoryImpl
import me.marthia.app.boomgrad.data.remote.repository.TourRepositoryImpl
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.repository.CategoryRepository
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import me.marthia.app.boomgrad.domain.repository.ProfileRepository
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository
import me.marthia.app.boomgrad.domain.repository.TourRepository
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetMockAttractionUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetTopAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.category.GetAttractionCategoryUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCityUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCountyUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetProvinceUseCase
import me.marthia.app.boomgrad.domain.usecase.login.ClearTokenUseCase
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import me.marthia.app.boomgrad.domain.usecase.profile.GetProfileUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetForYouToursUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetWeekRecommendedUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// Repository Module
val repositoryModule = module {

    singleOf(::AttractionApiServiceImpl) bind AttractionApiService::class
    single<AttractionRepository> { AttractionRepositoryImpl(get(), androidContext()) }

    single<TokenManager> { TokenManager(androidContext()) }

    // UseCases
    single<GetAttractionsUseCase> { GetAttractionsUseCase(get()) }
    single<GetMockAttractionUseCase> { GetMockAttractionUseCase(get()) }
    single<GetAttractionDetailUseCase> { GetAttractionDetailUseCase(get()) }
    single<LoginUseCase> { LoginUseCase(get()) }
    single<ClearTokenUseCase> { ClearTokenUseCase(get()) }

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

    single<GetProvinceUseCase> { GetProvinceUseCase(get()) }
    single<GetCountyUseCase> { GetCountyUseCase(get()) }
    single<GetCityUseCase> { GetCityUseCase(get()) }
    single<GetProfileUseCase> { GetProfileUseCase(get()) }

    single<GetAttractionCategoryUseCase> { GetAttractionCategoryUseCase(get()) }
    single<GetForYouToursUseCase> { GetForYouToursUseCase(get()) }
    single<GetTopAttractionsUseCase> { GetTopAttractionsUseCase(get()) }
    single<GetWeekRecommendedUseCase> { GetWeekRecommendedUseCase(get()) }
}