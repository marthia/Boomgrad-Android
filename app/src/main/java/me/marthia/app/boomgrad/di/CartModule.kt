package me.marthia.app.boomgrad.di

import me.marthia.app.boomgrad.data.remote.api.CartApiService
import me.marthia.app.boomgrad.data.remote.api.CartApiServiceImpl
import me.marthia.app.boomgrad.data.remote.repository.CartRepositoryImpl
import me.marthia.app.boomgrad.domain.repository.CartRepository
import me.marthia.app.boomgrad.domain.usecase.cart.AddCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.CheckoutUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.ConfirmCartPaymentUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.GetCartUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.RemoveCartItemUseCase
import me.marthia.app.boomgrad.domain.usecase.cart.UpdateCartItemUseCase
import me.marthia.app.boomgrad.presentation.cart.CartViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.util.Locale

val cartModule = module {

    // ── API ───────────────────────────────────────────────────────────────────
    single<CartApiService> { CartApiServiceImpl(client = get()) }

    // ── Repository ────────────────────────────────────────────────────────────
    single<CartRepository> { CartRepositoryImpl(api = get()) }

    // ── Use cases ─────────────────────────────────────────────────────────────
    factory { GetCartUseCase(get()) }
    factory { AddCartItemUseCase(get()) }
    factory { UpdateCartItemUseCase(get()) }
    factory { RemoveCartItemUseCase(get()) }
    factory { CheckoutUseCase(get()) }
    factory { ConfirmCartPaymentUseCase(get()) }

    viewModel {
        CartViewModel(
            getCart = get(),
            removeCartItem = get(),
            updateCartItem = get(),
            locale = Locale.of("fa", "IR"),   // swap to Locale.ENGLISH for EN builds
        )
    }
}
