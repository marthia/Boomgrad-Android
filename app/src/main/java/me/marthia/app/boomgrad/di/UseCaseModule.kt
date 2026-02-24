package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.attraction.GetTopAttractionsUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.AddCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.CheckoutUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.ConfirmCartPaymentUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.GetCartUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.RemoveCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.UpdateCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.category.GetAttractionCategoryUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCityUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCountyUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetProvinceUseCase
import me.marthia.app.boomgrad.domain.usecase.login.CheckAuthorizationUseCase
import me.marthia.app.boomgrad.domain.usecase.login.ClearTokenUseCase
import me.marthia.app.boomgrad.domain.usecase.login.LoginUseCase
import me.marthia.app.boomgrad.domain.usecase.profile.GetProfileUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetForYouToursUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetGuideInfoUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetTourDetailUseCase
import me.marthia.app.boomgrad.domain.usecase.tour.GetWeekRecommendedUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // attraction
    factory { GetAttractionsUseCase(get()) }
    factory { GetAttractionDetailUseCase(get()) }
    factory { GetTopAttractionsUseCase(get()) }


    factory { LoginUseCase(get()) }
    factory { ClearTokenUseCase(get()) }

    // geo
    factory { GetProvinceUseCase(get()) }
    factory { GetCountyUseCase(get()) }
    factory { GetCityUseCase(get()) }

    // profile
    factory { GetProfileUseCase(get()) }
    factory { CheckAuthorizationUseCase(get()) }
    factory { GetAttractionCategoryUseCase(get()) }

    // tours
    factory { GetForYouToursUseCase(get()) }
    factory { GetTourDetailUseCase(get()) }
    factory { GetWeekRecommendedUseCase(get()) }

    // guide
    factory { GetGuideInfoUseCase(get()) }


    // ── Use cases ─────────────────────────────────────────────────────────────
    factory { GetCartUseCase(get()) }
    factory { AddCartItemUseCase(get()) }
    factory { UpdateCartItemUseCase(get()) }
    factory { RemoveCartItemUseCase(get()) }
    factory { CheckoutUseCase(get()) }
    factory { ConfirmCartPaymentUseCase(get()) }
}