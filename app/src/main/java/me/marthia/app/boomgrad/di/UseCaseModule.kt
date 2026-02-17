package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetTopAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.category.GetAttractionCategoryUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCityUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCountyUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetProvinceUseCase
import me.marthia.app.boomgrad.domain.usecase.login.CheckAuthorizationUseCase
import me.marthia.app.boomgrad.domain.usecase.login.ClearTokenUseCase
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import me.marthia.app.boomgrad.domain.usecase.profile.GetProfileUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetForYouToursUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetTourDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetWeekRecommendedUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // attraction
    single<GetAttractionsUseCase> { GetAttractionsUseCase(get()) }
    single<GetAttractionDetailUseCase> { GetAttractionDetailUseCase(get()) }
    single<GetTopAttractionsUseCase> { GetTopAttractionsUseCase(get()) }


    single<LoginUseCase> { LoginUseCase(get()) }
    single<ClearTokenUseCase> { ClearTokenUseCase(get()) }

    // geo
    single<GetProvinceUseCase> { GetProvinceUseCase(get()) }
    single<GetCountyUseCase> { GetCountyUseCase(get()) }
    single<GetCityUseCase> { GetCityUseCase(get()) }

    // profile
    single<GetProfileUseCase> { GetProfileUseCase(get()) }
    single<CheckAuthorizationUseCase> { CheckAuthorizationUseCase(get()) }
    single<GetAttractionCategoryUseCase> { GetAttractionCategoryUseCase(get()) }

    // tours
    single<GetForYouToursUseCase> { GetForYouToursUseCase(get()) }
    single<GetTourDetailUseCase> { GetTourDetailUseCase(get()) }
    single<GetWeekRecommendedUseCase> { GetWeekRecommendedUseCase(get()) }
}